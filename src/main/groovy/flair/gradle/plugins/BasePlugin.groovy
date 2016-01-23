package flair.gradle.plugins

import flair.gradle.dependencies.Configurations
import flair.gradle.directoryWatcher.DirectoryWatcher
import flair.gradle.directoryWatcher.IWatcherAction
import flair.gradle.extensions.Extensions
import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.Properties
import flair.gradle.extensions.factories.FlairExtensionFactory
import flair.gradle.extensions.factories.IExtensionFactory
import flair.gradle.structure.ClassTemplateStructure
import flair.gradle.structure.CommonStructure
import flair.gradle.structure.IStructure
import flair.gradle.structure.VariantStructure
import flair.gradle.structure.generated.GenerateFontClass
import flair.gradle.structure.generated.GenerateRClass
import flair.gradle.tasks.Assemble
import flair.gradle.tasks.IVariantTask
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class BasePlugin extends AbstractPlugin implements IExtensionPlugin , IStructurePlugin , IWatcherActionPlugin , IConfigurationPlugin
{
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

				if( it == this )
				{
					flair = project.extensions.getByName( Extensions.FLAIR.name ) as IExtensionManager
				}

				// TODO try to remove platform extension if only one exist (actual comment code doesn't work)
				//if( it instanceof IPlatformPlugin && platformPluginCount == 2 )
				//{
				//	platformPlugins[ 0 ].extensionFactory.create( project.extensions.getByName( FlairExtension.NAME ) as ExtensionAware , project )
				//}
			}

			if( it instanceof IConfigurationPlugin )
			{
				createConfigurations( it.configurations )
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
	public List<Configurations> getConfigurations()
	{
		return Configurations.DEFAULTS
	}

	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )

		list.add( new CommonStructure( ) )
		list.add( new ClassTemplateStructure( ) )
		list.add( new VariantStructure( ) )

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

	private void createConfigurations( List<Configurations> list )
	{
		list.each { conf ->

			project.configurations.create( conf.name ) {

				if( conf.files )
				{
					conf.files.each {
						project.dependencies.add( project.configurations.getByName( conf.name ).name , project.files( "${ flair.getFlairProperty( Properties.MODULE_NAME.name ) }/${ it }" ) )
					}
				}

				if( conf.fileTree )
				{
					conf.fileTree.each {
						if( it.key == "dir" ) it.value = "${ flair.getFlairProperty( Properties.MODULE_NAME.name ) }/${ it.value }"
					}

					project.dependencies.add( conf.name , project.fileTree( conf.fileTree ) )
				}
			}
		}
	}

	private void createStructures()
	{
		String moduleName = flair.getFlairProperty( Properties.MODULE_NAME.name )
		String packageName = flair.getFlairProperty( Properties.PACKAGE_NAME.name )

		if( !moduleName || !packageName ) return

		String tempDir = System.getProperty( "java.io.tmpdir" )
		String scaffoldTempDir = "${ tempDir }/scaffold"

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).getPath( ) )
			into tempDir

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		GenerateFontClass.template = project.file( "${ scaffoldTempDir }/src/main/generated/Fonts.as" ).getText( )
		GenerateRClass.template = project.file( "${ scaffoldTempDir }/src/main/generated/R.as" ).getText( )

		project.plugins.each {
			if( it instanceof IStructurePlugin )
			{
				it.structures.each { structure -> structure.create( project , project.file( scaffoldTempDir ) )
				}
			}
		}

		project.file( scaffoldTempDir ).deleteDir( )
	}

	private void createVariantTasks()
	{
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
