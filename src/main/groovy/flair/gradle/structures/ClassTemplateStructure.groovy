package flair.gradle.structures

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ClassTemplateStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager
		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME )

		if( project.fileTree( "${ moduleName }/src/main/actionscript" ).size( ) > 0 ) return

		String packageName = extensionManager.getFlairProperty( FlairProperties.PACKAGE_NAME )

		String s = packageName.replace( "." , "/" )

		project.copy {
			from "${ source.path }/src/main/actionscript/_packageName_"
			into "${ moduleName }/src/main/actionscript/${ s }"
		}

		project.fileTree( "${ moduleName }/src/main/actionscript/${ s }" ).each { file ->

			file.write( file.text.replace( "_packageName_" , packageName ) )
		}
	}
}