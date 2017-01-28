package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.dependencies.Config
import flair.gradle.extensions.FlairProperty
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Asdoc extends AbstractTask
{
	protected ICli asdoc = new flair.gradle.cli.Asdoc( )

	Asdoc()
	{
		group = TaskGroup.DOCUMENTATION.name
		description = "Generates asdoc from source classes into ${ project.buildDir.name }/asdoc"
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void asdoc()
	{
		String srcRoot = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME ) + "/src"

		if( !srcRoot ) return

		asdoc.addArgument( "+configname=airmobile" )
		asdoc.addArgument( "-exclude-dependencies=true" )

		addAsLibraryPaths( )
		addLibraryPaths( )
		addSourcePaths( )

		asdoc.addArgument( "-output" )
		asdoc.addArgument( project.buildDir.path + "/asdoc" )

		asdoc.execute( project )
	}

	private void addSourcePaths()
	{
		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.SOURCE.name.toLowerCase( ) ) }.each {

			it.files.each { file ->

				if( file.exists( ) )
				{
					String path = file.path
					FileTree tree = project.fileTree( path )

					if( tree.size( ) > 0 ) asdoc.addArgument( "-source-path+=${ path }" )

					tree.each { asFile ->

						asdoc.addArgument( "-doc-classes" )
						asdoc.addArgument( asFile.path.replace( path + File.separator , "" ).replace( File.separator , "." ).replace( ".as" , "" ) )
					}
				}
			}
		}
	}

	private void addAsLibraryPaths()
	{
		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.AS_LIBRARY.name.toLowerCase( ) ) }.each {

			it.files.each { file ->
				if( file.exists( ) ) asdoc.addArgument( "-source-path+=${ file.path }" )
			}
		}
	}

	private void addLibraryPaths()
	{
		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.LIBRARY.name.toLowerCase( ) ) }.each {

			it.files.each { file ->

				if( file.exists( ) ) asdoc.addArgument( "-library-path+=${ file.path }" )
			}
		}
	}
}
