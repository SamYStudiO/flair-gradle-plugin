package flair.gradle.structures

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AtlasesStructure implements IStructure
{
	@Override
	void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( FlairProperty.MODULE_NAME )

		if( project.fileTree( "${ moduleName }/src/main/atlases" ).size( ) > 0 ) return

		project.copy {
			from "${ source.path }/src/main/atlases"
			into "${ moduleName }/src/main/atlases"
		}
	}
}
