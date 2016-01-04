package flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class ProcessProdAndroidResources extends DefaultTask
{
	public ProcessProdAndroidResources()
	{
		group = "process resources"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		project.getBuildDir( ).deleteDir( )

		String moduleName = project.flair.moduleName
		String androidExcludeResources = project.flair.androidExcludeResources

		project.copy {
			from "${ moduleName }/src/main/assets"
			into "${ project.getBuildDir( ) }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			exclude androidExcludeResources.split( "," )
			exclude "*-dev*/** *-preprod/**"
			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/android/splashs"
			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/android/icons"
			into "${ project.getBuildDir( ) }/icons"
		}
	}
}
