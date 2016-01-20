package flair.gradle.structure

import flair.gradle.extensions.IPlatformExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IPlatformExtensionManager ).getFlairProperty( "moduleName" )

		if( !project.file( "${ moduleName }/${ moduleName }.iml" ).exists( ) )
		{
			project.copy {
				from "${ source.path }/scaffold.iml"
				into moduleName

				rename "scaffold.iml" , "${ moduleName }.iml"
			}
		}

		if( !project.rootProject.file( ".idea/libraries" ).exists( ) )
		{
			project.copy {
				from "${ source.path }/idea/libraries"
				into "${ project.rootProject.rootDir }/.idea/libraries"
			}
		}

		project.rootProject.fileTree( ".idea/libraries" ).each { file ->

			file.write( file.getText( ).replace( '${moduleName}' , moduleName ) )
		}
	}
}