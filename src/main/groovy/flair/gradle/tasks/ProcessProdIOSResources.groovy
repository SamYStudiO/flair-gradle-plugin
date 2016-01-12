package flair.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessProdIOSResources extends DefaultTask
{
	public ProcessProdIOSResources()
	{
		group = Groups.DEFAULT.name
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

