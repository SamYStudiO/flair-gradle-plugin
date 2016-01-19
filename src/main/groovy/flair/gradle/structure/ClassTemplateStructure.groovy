package flair.gradle.structure

import flair.gradle.extensions.IPlatformExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ClassTemplateStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		IPlatformExtensionManager extensionManager = project.flair as IPlatformExtensionManager
		String moduleName = extensionManager.getFlairProperty( "moduleName" )

		if( project.fileTree( "${ moduleName }/src/main/actionscript" ).size( ) > 0 ) return

		String packageName = extensionManager.getFlairProperty( "packageName" )

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