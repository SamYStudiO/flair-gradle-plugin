package flair.gradle.plugins

import flair.gradle.directoryWatcher.DirectoryWatcher
import flair.gradle.directoryWatcher.IWatcherAction
import flair.gradle.directoryWatcher.actions.GenerateFontClass
import flair.gradle.directoryWatcher.actions.GenerateRClass
import flair.gradle.extensions.Extensions
import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.Properties
import flair.gradle.extensions.factories.FlairExtensionFactory
import flair.gradle.extensions.factories.IExtensionFactory
import flair.gradle.ide.Ide
import flair.gradle.ide.Idea
import flair.gradle.structure.ClassTemplateStructure
import flair.gradle.structure.CommonStructure
import flair.gradle.structure.IStructure
import flair.gradle.structure.VariantStructure
import flair.gradle.tasks.Assemble
import flair.gradle.tasks.IVariantTask
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class BasePlugin extends AbstractPlugin implements IPlugin , IExtensionPlugin , IStructurePlugin , IWatcherActionPlugin
{
	private List<Ide> ides = new ArrayList<Ide>( )

	private List<IPlugin> plugins = new ArrayList<IPlugin>( )

	private List<IPlatformPlugin> platformPlugins = new ArrayList<IPlatformPlugin>( )

	private int platformPluginCount = 0

	private IExtensionManager flair

	private DirectoryWatcher directoryWatcher

	public BasePlugin()
	{
	}

	@Override
	public void apply( Project project )
	{
		super.apply( project )

		addIdes( )

		project.plugins.whenPluginAdded {

			if( it instanceof IPlugin ) plugins.add( it )
			if( it instanceof IPlatformPlugin )
			{
				platformPlugins.add( it )
				platformPluginCount++
			}

			if( it instanceof IExtensionPlugin /*&& !( it instanceof IPlatformPlugin && platformPluginCount < 2 )*/ )
			{
				it.extensionFactory.create( it == this ? project : project.extensions.getByName( Extensions.FLAIR.name ) as ExtensionAware , project )

				if( it == this ) flair = project.extensions.getByName( Extensions.FLAIR.name ) as IExtensionManager

				// TODO try to remove platform extension if only one exist (actual comment code doesn't work)
				//if( it instanceof IPlatformPlugin && platformPluginCount == 2 )
				//{
				//	platformPlugins[ 0 ].extensionFactory.create( project.extensions.getByName( FlairExtension.NAME ) as ExtensionAware , project )
				//}
			}
		}

		project.afterEvaluate {

			createStructures( )
			createVariantTasks( )
			initDirectoryWatcher( )
			addDirectoryWatcherActions( )
		}
	}

	@Override
	public IExtensionFactory getExtensionFactory()
	{
		return new FlairExtensionFactory( )
	}

	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )

		list.add( new CommonStructure( ) )
		list.add( new ClassTemplateStructure( ) )
		list.add( new VariantStructure( ) )

		ides.each {
			if( it.isActive ) list.add( it.structure )
		}

		return list
	}

	@Override
	public Map<? , IWatcherAction> getWatcherActions()
	{
		Map<? , IWatcherAction> map = new HashMap<? , IWatcherAction>( )

		map.put( File.separator + "resources" , new GenerateRClass( ) )
		map.put( File.separator + "fonts" , new GenerateFontClass( ) )

		return map
	}

	@Override
	protected void addTasks()
	{
		project.tasks.create( Tasks.CLEAN.name , Tasks.CLEAN.type )
	}

	private void addIdes()
	{
		ides.add( new Idea( project ) )
	}

	private void createStructures()
	{
		String moduleName = flair.getFlairProperty( Properties.MODULE_NAME.name )
		String packageName = flair.getFlairProperty( Properties.PACKAGE_NAME.name )

		if( !moduleName || !packageName ) return

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).getPath( ) )
			into System.getProperty( "java.io.tmpdir" )

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		project.plugins.each {
			if( it instanceof IStructurePlugin )
			{
				it.structures.each { structure -> structure.create( project , project.file( "${ System.getProperty( "java.io.tmpdir" ) }/scaffold" ) )
				}
			}
		}

		project.file( "${ System.getProperty( "java.io.tmpdir" ) }/scaffold" ).deleteDir( )
	}

	private void createVariantTasks()
	{
		List<Class> list = new ArrayList<Class>( )

		project.plugins.each { plugin ->
			if( plugin instanceof IVariantTaskPlugin )
			{
				plugin.variantTaskFactories.each { factory ->

					flair.getPlatformVariants( plugin instanceof IPlatformPlugin ? plugin.platform : null ).each { variant ->

						IVariantTask task = factory.create( project , variant )

						if( task instanceof Assemble && PluginManager.hasPlugin( project , TexturePackerPlugin ) )
						{
							task.dependsOn project.tasks.getByName( Tasks.PUBLISH_ATLASES.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE ) ).name
						}

						list.add( factory.class )
					}
				}
			}
		}
	}

	private initDirectoryWatcher()
	{
		directoryWatcher = new DirectoryWatcher( project )
		Thread t = new Thread( directoryWatcher )
		t.start( )
	}

	private addDirectoryWatcherActions()
	{
		project.plugins.each {
			if( it instanceof IWatcherActionPlugin )
			{
				it.watcherActions.each { action ->

					if( action.key instanceof String )
					{
						directoryWatcher.watch( ( String ) action.key , action.value )
					}
					else if( action.key instanceof File )
					{
						directoryWatcher.watch( ( File ) action.key , action.value )
					}

					action.value.execute( project )
				}
			}
		}
	}
}
