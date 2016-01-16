package flair.gradle.tasks.scaffold

import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.tasks.Group
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Scaffold extends DefaultTask
{
	public Scaffold()
	{
		group = Group.SCAFFOLD.name
		description = ""
	}

	@TaskAction
	public void generateProject()
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )
		String packageName = PropertyManager.getProperty( project , "packageName" )
		String appName = PropertyManager.getProperty( project , "appDescriptor" , "appName" , null )

		if( packageName.isEmpty( ) ) throw new IllegalArgumentException( String.format( "Missing packageName property add%nflair {%n	packageName = \"myAppid\"%n}%nto your build.gradle file." ) )
		if( project.file( moduleName ).exists( ) ) throw new Exception( "Scaffold already done." )

		String s = packageName.replace( "." , "/" )

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).toExternalForm( ) )
			into project.getRootDir( )

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		FileTree tree = project.fileTree( "scaffold" )

		tree.each { file ->

			if( file.isFile( ) )
			{
				String ext = file.getName( ).split( "\\." ).last( )
				String allowedExt = "xml,as,iml"

				if( allowedExt.indexOf( ext ) >= 0 )
				{
					String content = file.getText( )

					content = content.replace( '${packageName}' , packageName )
							.replace( "_appId_" , packageName )
							.replace( '${projectName}' , project.getName( ) )
							.replace( '${appName}' , appName )
							.replace( '${moduleName}' , moduleName )

					file.write( content )
				}
			}
		}

		project.copy {
			from "scaffold/src/main/actionscript/_appId_"
			into "scaffold/src/main/actionscript/${ s }"
		}

		project.file( "scaffold" ).renameTo( moduleName )
		project.file( "app/src/main/actionscript/_appId_" ).deleteDir( )

		project.copy {
			from "${ moduleName }/libraries"
			into ".idea/libraries"
		}

		project.file( "${ moduleName }/libraries" ).deleteDir( )

		project.copy {
			from "${ moduleName }/runConfigurations"
			into ".idea/runConfigurations"
		}

		project.file( "${ moduleName }/runConfigurations" ).deleteDir( )

		project.copy {
			from "${ moduleName }/gitignore"
			into project.getRootDir( )

			rename( "gitignore" , ".gitignore" )
		}

		project.file( "${ moduleName }/gitignore" ).delete( )

		project.copy {
			from "${ moduleName }/local.properties"
			into project.getRootDir( )
		}

		project.file( "${ moduleName }/local.properties" ).delete( )

		project.file( ".idea/modules.xml" ).write( project.file( "${ moduleName }/modules.xml" ).getText( ) )
		project.file( "${ moduleName }/modules.xml" ).delete( )
		project.file( "${ moduleName }/scaffold.iml" ).renameTo( "${ moduleName }/${ moduleName }.iml" )
	}
}
