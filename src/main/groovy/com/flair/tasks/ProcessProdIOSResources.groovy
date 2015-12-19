package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class ProcessProdIOSResources extends DefaultTask
{
	public ProcessProdIOSResources()
	{
		group = "process resources"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		project.getBuildDir( ).deleteDir( )

		String moduleName = project.flair.moduleName
		String iosExcludeResources = project.flair.iosExcludeResources

		project.copy {
			from "${ moduleName }/src/main/assets"
			into "${ project.getBuildDir( ) }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			exclude iosExcludeResources.split( "," )
			exclude "*-dev*/** *-preprod/**"
			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/ios/splashs"
			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/ios/icons"
			into "${ project.getBuildDir( ) }/icons"
		}
	}
}

