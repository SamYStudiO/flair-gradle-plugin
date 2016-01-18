package flair.gradle.structure

import flair.gradle.extensions.PropertyManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( !moduleName || project.fileTree( "${ moduleName }/src/android" ).size( ) > 0 ) return

		project.copy {
			from "${ source.path }/src/android"
			into "${ moduleName }/src/android"
		}
	}
}
