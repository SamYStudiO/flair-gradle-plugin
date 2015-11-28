package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class ProcessIOSResources extends DefaultTask
{
	public ProcessIOSResources()
	{
		group = "processResources"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		String moduleName = project.flair.moduleName
		String commonResources = project.flair.commonResources
		String iosResources = project.flair.iosResources

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			include commonResources
			include iosResources
		}

		project.copy {
			from "${ moduleName }/src/main/resources/ios/splashs"

			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/ios/icons"

			into "${ project.getBuildDir( ) }/icons"
		}
	}
}

