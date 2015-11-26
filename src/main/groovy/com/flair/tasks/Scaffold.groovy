package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 08/11/2015.
 */
public class Scaffold extends DefaultTask
{
	public Scaffold()
	{
		group = "scaffold"
		description = ""
	}

	@TaskAction
	public void generateProject()
	{
		String appId = project.flair.appId
		String moduleName = project.flair.moduleName

		if( appId.isEmpty( ) ) throw new IllegalArgumentException( "Missing appId property add\nflair {\n	appId = \"myAppid\"\n}\nto your build.gradle file." )
		if( project.file( moduleName ).exists( ) ) throw new Exception( "Scaffold already done." )

		String s = appId.replace( "." , File.separator )

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).toExternalForm( ) )
			//from project.zipTree( f )
			into project.getRootDir( )

			//rename( /scaffold.iml/ , "${ moduleName }.iml" )

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		File imlFile = project.file( "/scaffold/scaffold.iml" )
		String imlContent = project.file( "/scaffold/scaffold.iml" ).getText( )

		imlContent = imlContent.replace( "\${appId}" , appId )
				.replace( "_appId_" , appId )
				.replace( "\${projectName}" , project.name )
				.replace( "\${moduleName}" , moduleName )

		project.file( "/scaffold/${ moduleName }.iml" ).write( imlContent )
		imlFile.delete( )

		project.copy {
			from "scaffold/src/main/actionscript/_appId_"
			into "scaffold/src/main/actionscript/${ s }"

			filter {
				println( )
				it.replace( "\${appId}" , appId )
						.replace( "_appId_" , appId )
						.replace( "\${projectName}" , project.name )
						.replace( "\${moduleName}" , moduleName )
			}
		}

		project.file( "scaffold" ).renameTo( moduleName )
		project.file( "app/src/main/actionscript/_appId_" ).deleteDir( )

		//project.file( "${ moduleName }/scaffold.iml" ).renameTo( "${ moduleName }/${ moduleName }.iml" )

		project.copy {
			from "${ moduleName }/libraries"
			into ".idea/libraries"
		}

		project.file( "${ moduleName }/libraries" ).deleteDir( )

		project.copy {
			from "${ moduleName }/modules.xml"
			into ".idea/"

			filter {
				println( )
				it.replace( "\${appId}" , appId )
						.replace( "\${projectName}" , project.name )
						.replace( "\${moduleName}" , moduleName )
			}
		}

		project.file( "${ moduleName }/modules.xml" ).delete( )
	}
}
