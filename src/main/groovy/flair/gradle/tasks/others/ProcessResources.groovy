package flair.gradle.tasks.others

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessResources extends DefaultTask
{
	public Platform platform

	public ProcessResources()
	{
		group = Group.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void copy()
	{
		project.getBuildDir( ).deleteDir( )

		String moduleName = project.flair.moduleName
		String platform = platform.name.toLowerCase( )
		String excludeResources = project.flair.excludeResources
		String platformExcludeResources = project.flair[ platform ].excludeResources.toString( )

		if( platformExcludeResources != excludeResources ) excludeResources = excludeResources.concat( "," + platformExcludeResources )


		project.copy {
			from "${ moduleName }/src/main/assets"
			into "${ project.getBuildDir( ) }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/resources/"
			into "${ project.getBuildDir( ) }/resources/"

			exclude excludeResources.split( "," )

			includeEmptyDirs = false
		}

		project.copy {
			from "${ moduleName }/src/main/${ platform }/splashs"
			into "${ project.getBuildDir( ) }/"
		}

		project.copy {
			from "${ moduleName }/src/main/${ platform }/icons"
			into "${ project.getBuildDir( ) }/icons"
		}
	}
}
