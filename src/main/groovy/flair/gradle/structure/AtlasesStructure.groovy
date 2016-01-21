package flair.gradle.structure

import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.Properties
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AtlasesStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( Properties.MODULE_NAME.name )

		if( project.fileTree( "${ moduleName }/src/main/atlases" ).size( ) > 0 ) return

		project.copy {
			from "${ source.path }/src/main/atlases"
			into "${ moduleName }/src/main/atlases"
		}
	}
}
