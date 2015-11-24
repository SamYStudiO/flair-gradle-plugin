package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
class CopyAndroidResources extends DefaultTask
{
	public CopyAndroidResources()
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
			from "${ moduleName }/src/main/resources/drawable-hhdpi"

			into "${ project.buildDir }/resources/drawable-hhdpi"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/drawable-xhdpi"

			into "${ project.buildDir }/resources/drawable-xhdpi"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/drawable-xxhdpi"

			into "${ project.buildDir }/resources/drawable-xxhdpi"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/drawable-xxxhdpi"

			into "${ project.buildDir }/resources/drawable-xxxhdpi"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/values"

			into "${ project.buildDir }/resources/values"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/android/splashs"

			into "${ project.buildDir }/"
		}

		project.copy {
			from "${ moduleName }/src/main/resources/android/icons"

			into "${ project.buildDir }/icons"
		}
	}
}
