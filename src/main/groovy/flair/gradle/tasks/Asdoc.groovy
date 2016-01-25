package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.dependencies.Configurations
import flair.gradle.extensions.Properties
import flair.gradle.plugins.PluginManager
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Asdoc extends AbstractTask
{
	protected ICli asdoc = new flair.gradle.cli.Asdoc( )

	public Asdoc()
	{
		group = Groups.DOCUMENTATION.name
		description = ""
	}

	@TaskAction
	public void asdoc()
	{
		String srcRoot = extensionManager.getFlairProperty( Properties.MODULE_NAME.name ) + "/src"

		if( !srcRoot ) return

		asdoc.addArgument( "+configname=airmobile" )
		asdoc.addArgument( "-exclude-dependencies=true" )

		addAsLibraryPaths( )
		addLibraryPaths( )



		List<String> list = new ArrayList<String>( )

		list.add( "main" )

		PluginManager.getCurrentPlatforms( project ).each { list.add( it.name ) }
		extensionManager.allActivePlatformProductFlavors.each { list.add( it.name ) }
		extensionManager.allActivePlatformBuildTypes.each { list.add( it.name ) }

		list.each {

			String path = project.file( "${ srcRoot }/${ it }/actionscript" ).path
			FileTree tree = project.fileTree( path )

			if( tree.size( ) > 0 ) asdoc.addArgument( "-source-path+=${ path }" )

			tree.each { file ->

				asdoc.addArgument( "-doc-classes" )
				asdoc.addArgument( file.path.replace( path + File.separator , "" ).replace( File.separator , "." ).replace( ".as" , "" ) )
			}

			if( it == "main" )
			{
				path = project.file( "${ srcRoot }/${ it }/generated" ).path
				tree = project.fileTree( path )

				if( tree.size( ) > 0 ) asdoc.addArgument( "-source-path+=${ path }" )

				tree.each { file ->

					asdoc.addArgument( "-doc-classes" )
					asdoc.addArgument( file.path.replace( path + File.separator , "" ).replace( File.separator , "." ).replace( ".as" , "" ) )
				}
			}
		}

		asdoc.addArgument( "-output" )
		asdoc.addArgument( project.buildDir.path + "/asdoc" )

		asdoc.execute( project )
	}

	private void addAsLibraryPaths()
	{
		project.configurations.getByName( Configurations.COMPILE.name ).files.each {

			asdoc.addArgument( "-source-path+=${ it }" )
		}

		PluginManager.getCurrentPlatforms( project ).each {

			project.configurations.getByName( "${ it.name }Compile" ).files.each {

				asdoc.addArgument( "-source-path+=${ it }" )
			}
		}

		extensionManager.allActivePlatformProductFlavors.each {
			project.configurations.getByName( "${ it.name }Compile" ).files.each {

				asdoc.addArgument( "-source-path+=${ it }" )
			}
		}

		extensionManager.allActivePlatformBuildTypes.each {
			project.configurations.getByName( "${ it.name }Compile" ).files.each {

				asdoc.addArgument( "-source-path+=${ it }" )
			}
		}
	}

	private void addLibraryPaths()
	{
		project.configurations.getByName( Configurations.LIBRARY_COMPILE.name ).files.each {

			asdoc.addArgument( "-library-path+=${ it }" )
		}

		PluginManager.getCurrentPlatforms( project ).each {
			project.configurations.getByName( "${ it.name }LibraryCompile" ).files.each {

				asdoc.addArgument( "-library-path+=${ it }" )
			}
		}

		extensionManager.allActivePlatformProductFlavors.each {
			project.configurations.getByName( "${ it.name }LibraryCompile" ).files.each {

				asdoc.addArgument( "-library-path+=${ it }" )
			}
		}

		extensionManager.allActivePlatformBuildTypes.each {
			project.configurations.getByName( "${ it.name }LibraryCompile" ).files.each {

				asdoc.addArgument( "-library-path+=${ it }" )
			}
		}
	}
}
