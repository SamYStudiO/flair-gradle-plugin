package flair.gradle.structures

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CommonStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( FlairProperties.MODULE_NAME )

		if( project.fileTree( "${ moduleName }/src/main" ).size( ) == 0 )
		{

			project.copy {
				from "${ source.path }/src/main"
				into "${ moduleName }/src/main"

				exclude "**/ios/**" , "**/android/**" , "**/desktop/**" , "**/_packageName_/**" , "**/atlases/**"
			}

			project.copy {
				from "${ source.path }/libs_ane"
				into "${ moduleName }/libs_ane"
			}

			project.copy {
				from "${ source.path }/libs_as"
				into "${ moduleName }/libs_as"
			}

			project.copy {
				from "${ source.path }/libs_swc"
				into "${ moduleName }/libs_swc"
			}
		}

		if( !project.file( "${ project.rootDir.path }/local.properties" ).exists( ) )
		{
			project.copy {
				from "${ source.path }/local.properties"
				into project.rootDir
			}
		}
	}
}
