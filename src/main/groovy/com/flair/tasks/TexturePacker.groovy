package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 24/11/2015.
 */
class TexturePacker extends DefaultTask
{
	public TexturePacker()
	{
		group = "texturePacker"
		description = ""
	}

	@TaskAction
	public void generateTextures()
	{
		String moduleName = project.flair.moduleName

		project.exec {
			commandLine "TexturePacker" , "${ moduleName }/src/main/resources/assets/global.tps"
		}
	}
}
