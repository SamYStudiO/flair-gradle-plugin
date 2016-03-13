package flair.gradle.structures.idea

import flair.gradle.dependencies.Config
import flair.gradle.structures.IStructure
import org.gradle.api.Project
import org.gradle.api.file.FileTree

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaLibrariesStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		List<String> libraries = new ArrayList<String>( )
		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.LIBRARY.name ) }.each {

			it.files.each { file ->

				String path = file.isDirectory( ) ? file.path : file.parentFile.path

				if( !libraries.contains( path ) ) libraries.add( path )
			}
		}

		project.file( ".idea/libraries/" ).listFiles( ).each { if( it.name.startsWith( "flair_" ) ) it.delete( ) }
		libraries.each { path ->

			File pathFile = project.file( path )
			File output = project.file( "${ project.rootDir.path }/.idea/libraries" )
			String template = "libs_template"
			FileTree tree = project.fileTree( pathFile )
			if( tree.files.size( ) && tree.files[ 0 ].name.split( "\\." )[ 1 ].toLowerCase( ) == "as" ) template = "libs_as_template"

			project.copy {
				from "${ source.path }/idea/${ template }.xml"
				into output

				rename "${ template }.xml" , "${ pathFile.name }.xml"
			}

			File file = project.file( "${ output }/${ pathFile.name }.xml" )
			file.createNewFile( )

			file.write( file.text.replaceAll( "\\{libName\\}" , pathFile.name ).replaceAll( "\\{path\\}" , pathFile.path.replace( project.rootDir.path + File.separator , "" ).replaceAll( "\\\\" , "/" ) ) )
		}
	}
}