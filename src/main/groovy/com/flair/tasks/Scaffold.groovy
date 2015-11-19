package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 08/11/2015.
 */
class Scaffold extends DefaultTask
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

		if( appId.isEmpty() ) throw new IllegalArgumentException( "Missing appId property add\nflair {\n	appId = \"myAppid\"\n}\nto your build.gradle file." )
		if( project.file( moduleName ).exists() ) throw new Exception( "Scaffold already done." )

		String s = appId.replace( ".", File.separator )

		project.copy {
			from project.zipTree( getClass().getProtectionDomain().getCodeSource().getLocation().toExternalForm() )
			into project.getRootDir()

			filter {
				it.replace( "\${appId}", appId )
						.replace( "_appId_", appId )
						.replace( "\${projectName}", project.name )
			}

			include "scaffold/**"
			exclude "**/*.iml"
		}

		project.copy {
			from "scaffold/src/main/actionscript/_appId_"
			into "scaffold/src/main/actionscript/${ s }"
		}
		println( project.file( "scaffold" ).renameTo( moduleName ) )
		println( project.file( "app/src/main/actionscript/_appId_" ).deleteDir() )
	}
}
