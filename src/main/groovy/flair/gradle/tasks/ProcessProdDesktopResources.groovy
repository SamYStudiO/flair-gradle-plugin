package flair.gradle.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessProdDesktopResources extends DefaultTask
{
	public ProcessProdDesktopResources()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void copy()
	{
		project.getBuildDir( ).deleteDir( )

		String moduleName = project.flair.moduleName
		String desktopExcludeResources = project.flair.desktopExcludeResources

		project.copy {
			from "${ moduleName }/src/main/assets"
			into "${ project.getBuildDir( ) }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			exclude desktopExcludeResources.split( "," )
			exclude "*-dev*/** *-preprod/**"
			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/desktop/splashs"
			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/desktop/icons"
			into "${ project.getBuildDir( ) }/icons"
		}
	}
}
