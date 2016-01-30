package flair.gradle.plugins

import flair.gradle.dependencies.Configurations
import flair.gradle.dependencies.Sdk
import flair.gradle.directoryWatcher.DirectoryWatcher
import flair.gradle.directoryWatcher.IWatcherAction
import flair.gradle.directoryWatcher.generated.GenerateFontClass
import flair.gradle.directoryWatcher.generated.GenerateRClass
import flair.gradle.extensions.Extensions
import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.factories.FlairExtensionFactory
import flair.gradle.extensions.factories.IExtensionFactory
import flair.gradle.structures.CommonStructure
import flair.gradle.structures.IStructure
import flair.gradle.structures.VariantStructure
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import flair.gradle.variants.Variant.NamingTypes
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class BasePlugin extends AbstractPlugin implements IExtensionPlugin , IStructurePlugin , IWatcherActionPlugin , IConfigurationPlugin
{
	private List<IPlugin> plugins = new ArrayList<IPlugin>( )

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

			if( it instanceof IExtensionPlugin )
			{
				it.extensionFactory.create( it == this ? project : project.extensions.getByName( Extensions.FLAIR.name ) as ExtensionAware , project )

				if( it == this )
				{
					flair = project.extensions.getByName( Extensions.FLAIR.name ) as IExtensionManager
				}
			}

			if( it instanceof IConfigurationPlugin )
			{
				// can't do after evaluation since evaluation may need them for dependencies
				createConfigurations( it.configurations )
			}
		}

		checkLocalProperties( )

		project.afterEvaluate {

			if( isReady( ) )
			{
				createStructures( )
				createVariantTasks( )
				initDirectoryWatcher( )

				List<String> list = new ArrayList<String>( )

				flair.allActivePlatformVariants.each {

					list.add( Tasks.ASSEMBLE.name + it.getNameWithType( NamingTypes.CAPITALIZE ) )
				}

				Task t = project.tasks.create( Tasks.ASSEMBLE.name )
				t.group = Tasks.ASSEMBLE.group.name
				t.dependsOn list
			}
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
		project.tasks.create( Tasks.ASDOC.name , Tasks.ASDOC.type )
	}

	private boolean isReady()
	{
		boolean hasPackageName = flair.getFlairProperty( FlairProperties.PACKAGE_NAME ) as boolean
		boolean hasValidSdk = new Sdk( project ).isAirSdk( )

		if( !hasValidSdk )
		{
			throw new Exception( "Cannot find AIR SDK home, set a valid AIR SDK home from your local.properties file under project root" )
		}
		if( !hasPackageName )
		{
			throw new Exception( String.format( "Missing flair property packageName, add it to your build.gradle file :%nflair {%npackageName \"com.hello.world\"%n}" ) )
		}

		return true
	}

	private void checkLocalProperties()
	{
		File file = project.file( "${ project.rootDir.path }/local.properties" )

		if( !file.exists( ) )
		{
			file.createNewFile( )
			file.write( String.format( "## This file should *NOT* be checked into Version Control Systems,%n# as it contains information specific to your local configuration.%n#%n# Location of the Adobe AIR SDK. This is only used by Gradle.%n# For customization when using a Version Control System, please read the%n# header note.%nsdk.dir=" ) )
		}
	}

	private void createConfigurations( List<Configurations> list )
	{
		list.each { conf ->

			project.configurations.create( conf.name ) {

				if( conf.files )
				{
					conf.files.each {
						project.dependencies.add( conf.name , project.files( "${ flair.getFlairProperty( FlairProperties.MODULE_NAME ) }/${ it }" ) )
					}
				}

				if( conf.fileTree )
				{
					Map<String , String> map = conf.fileTree.clone( ) as Map<String , String>

					map.each {

						if( it.key == "dir" ) it.value = "${ flair.getFlairProperty( FlairProperties.MODULE_NAME ) }/${ it.value }"
					}

					project.dependencies.add( conf.name , project.fileTree( map ) )
				}
			}
		}
	}

	private void createStructures()
	{
		String moduleName = flair.getFlairProperty( FlairProperties.MODULE_NAME )
		String packageName = flair.getFlairProperty( FlairProperties.PACKAGE_NAME )

		if( !moduleName || !packageName ) return

		String tempDir = System.getProperty( "java.io.tmpdir" )
		String scaffoldTempDir = "${ tempDir }/scaffold"

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).getPath( ) )
			into tempDir

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		GenerateFontClass.template = project.file( "${ scaffoldTempDir }/src/main/generated/Fonts.as" ).text
		GenerateRClass.template = project.file( "${ scaffoldTempDir }/src/main/generated/R.as" ).text

		project.plugins.each {
			if( it instanceof IStructurePlugin )
			{
				it.structures.each { structure -> structure.create( project , project.file( scaffoldTempDir ) ) }
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

					boolean hasVariant = false

					flair.getPlatformVariants( plugin instanceof IPlatformPlugin ? plugin.platform : null ).each { variant ->

						factory.create( project , variant )

						hasVariant = true
					}

					if( !hasVariant && plugin instanceof IPlatformPlugin )
					{
						factory.create( project , new Variant( project , plugin.platform ) )
					}
				}
			}
		}
	}

	private initDirectoryWatcher()
	{
		File file = project.file( flair.getFlairProperty( FlairProperties.MODULE_NAME ) )

		if( file.exists( ) )
		{
			directoryWatcher = new DirectoryWatcher( project , file )
			Thread t = new Thread( directoryWatcher )
			t.start( )

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
}
