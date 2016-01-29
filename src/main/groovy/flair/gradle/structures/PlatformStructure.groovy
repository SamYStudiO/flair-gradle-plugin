package flair.gradle.structures

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PlatformStructure implements IStructure
{
	private Platforms platform

	public Platforms getPlatform()
	{
		return platform
	}

	public PlatformStructure( Platforms platform )
	{
		this.platform = platform
	}

	@Override
	public void create( Project project , File source )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager
		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME )

		if( project.fileTree( "${ moduleName }/src/${ platform.name }" ).size( ) > 0 ) return

		String packageName = extensionManager.getFlairProperty( FlairProperties.PACKAGE_NAME )
		String s = packageName.replace( "." , "/" )

		project.copy {
			from "${ source.path }/src/${ platform.name }"
			into "${ moduleName }/src/${ platform.name }"
		}

		project.fileTree( "${ moduleName }/src/${ platform.name }/actionscript/${ s }" ).each { file ->

			file.write( file.text.replace( "_packageName_" , packageName ) )
		}
	}
}
