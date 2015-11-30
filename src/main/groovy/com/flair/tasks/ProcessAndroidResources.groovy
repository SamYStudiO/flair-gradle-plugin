package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class ProcessAndroidResources extends DefaultTask
{
	public ProcessAndroidResources()
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
		String androidResources = project.flair.androidResources
		String resources = commonResources.concat( "," + androidResources )

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
			from "${ moduleName }/src/main/resources/android/icons"

			into "${ project.getBuildDir( ) }/icons"
		}
	}
}
