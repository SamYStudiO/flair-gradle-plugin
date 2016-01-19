package flair.gradle.structure

import flair.gradle.extensions.IPlatformExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IosStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IPlatformExtensionManager ).getFlairProperty( "moduleName" )

		if( project.fileTree( "${ moduleName }/src/ios" ).size( ) > 0 ) return

		project.copy {
			from "${ source.path }/src/ios"
			into "${ moduleName }/src/ios"
		}
	}
}
