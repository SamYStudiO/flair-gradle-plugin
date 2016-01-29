package flair.gradle.structures

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( FlairProperties.MODULE_NAME )

		if( project.fileTree( "${ moduleName }/src/android" ).size( ) > 0 ) return

		project.copy {
			from "${ source.path }/src/android"
			into "${ moduleName }/src/android"
		}
	}
}
