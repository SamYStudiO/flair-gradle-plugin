package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
class CopyIOSResources extends DefaultTask
{
	public CopyIOSResources()
	{
		group = "copy"
		description = ""
	}

	@TaskAction
	public void copy()
	{
		String moduleName = project.flair.moduleName

		project.copy {
			from "${ moduleName }/src/main/resources/drawable-mdpi"

			into "${ project.buildDir }/resources/drawable-mdpi"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/drawable-xhdpi"

			into "${ project.buildDir }/resources/drawable-xhdpi"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/values"

			into "${ project.buildDir }/resources/values"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/ios/splashs"

			into "${ project.buildDir }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/ios/icons"

			into "${ project.buildDir }/icons"
		}
	}
}

