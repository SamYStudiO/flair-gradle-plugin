package flair.gradle.structures

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.utils.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PlatformStructure implements IStructure
{
	private Platform platform

	Platform getPlatform()
	{
		return platform
	}

	PlatformStructure( Platform platform )
	{
		this.platform = platform
	}

	@Override
	void create( Project project , File source )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager
		String moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )

		if( project.fileTree( "${ moduleName }/src/${ platform.name }" ).size( ) > 0 ) return

		String packageName = extensionManager.getFlairProperty( FlairProperty.PACKAGE_NAME )
		String s = packageName.replace( "." , "/" )

		project.copy {
			from "${ source.path }/src/${ platform.name }"
			into "${ moduleName }/src/${ platform.name }"

			exclude "**/_packageName_/**"
		}

		project.copy {
			from "${ source.path }/src/${ platform.name }/actionscript/_packageName_"
			into "${ moduleName }/src/${ platform.name }/actionscript/${ s }"
		}

		project.fileTree( "${ moduleName }/src/${ platform.name }/actionscript/${ s }" ).each { file ->

			file.write( file.text.replace( "_packageName_" , packageName ) )
		}

		if( platform != Platform.IOS )
		{
			String charset = 'abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!$#{()}@%?_[]-+=*/'
			String password = ""
			int l = Math.round( Math.random( ) * 10 ) + 10

			for( int i = 0; i < l; i++ )
			{
				int r = Math.round( Math.random( ) * charset.length( ) )
				password += charset.charAt( r )
			}

			ICli adt = new Adt( )
			adt.addArgument( "-certificate" )
			adt.addArgument( "-cn" )
			adt.addArgument( project.name )
			adt.addArgument( "2048-RSA" )
			adt.addArgument( project.file( "${ moduleName }/src/${ platform.name }/signing/certificate.p12" ).path )
			adt.addArgument( password )
			adt.execute( project , platform )

			project.file( "${ moduleName }/src/${ platform.name }/signing/password.txt" ).write( password )
		}
	}
}
