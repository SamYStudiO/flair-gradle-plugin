package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class CopyDesktopResources extends DefaultTask
{
	public CopyDesktopResources()
	{
		group = "copy"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		String moduleName = project.flair.moduleName
		String commonResources = project.flair.commonResources
		String desktopResources = project.flair.desktopResources

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			include commonResources
			include desktopResources
		}

		project.copy {
			from "${ moduleName }/src/main/resources/desktop/splashs"

			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/desktop/icons"

			into "${ project.getBuildDir( ) }/icons"
		} s
	}
}
