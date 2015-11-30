package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class ProcessDesktopResources extends DefaultTask
{
	public ProcessDesktopResources()
	{
		group = "processResources"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		project.getBuildDir( ).deleteDir( )

		String moduleName = project.flair.moduleName
		String commonResources = project.flair.commonResources
		String desktopResources = project.flair.desktopResources
		String resources = commonResources.concat( "," + desktopResources )

		project.copy {
			from "${ moduleName }/src/main/assets"
			into "${ project.getBuildDir( ) }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			include resources.split( "," )
			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/android/splashs"
			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/desktop/splashs"

			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/desktop/icons"

			into "${ project.getBuildDir( ) }/icons"
		}
	}
}
