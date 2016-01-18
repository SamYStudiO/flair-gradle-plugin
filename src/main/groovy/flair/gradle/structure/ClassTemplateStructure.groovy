package flair.gradle.structure

import flair.gradle.extensions.PropertyManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ClassTemplateStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( !moduleName ) return

		String packageName = PropertyManager.getProperty( project , "packageName" )

		if( !packageName || project.fileTree( "${ moduleName }/src/main/actionscript" ).size( ) > 0 ) return

		String s = packageName.replace( "." , "/" )

		project.copy {
			from "${ source.path }/src/main/actionscript/_packageName_"
			into "${ moduleName }/src/main/actionscript/${ s }"
		}

		project.fileTree( "${ moduleName }/src/main/actionscript/${ s }" ).each { file ->

			file.write( file.getText( ).replace( "_packageName_" , packageName ) )
		}
	}
}