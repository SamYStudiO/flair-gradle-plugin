package flair.gradle.tasks.others

import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.platforms.Platform
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Group
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessResources extends AbstractVariantTask
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
		String moduleName = PropertyManager.getProperty( project , "moduleName" )
		String platform = platform.name.toLowerCase( )
		String excludeResources = PropertyManager.getProperty( project , "excludeResources" )

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
