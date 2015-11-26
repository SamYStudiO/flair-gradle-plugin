package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
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
		FileTree tree = project.fileTree( "${ moduleName }/src/main/resources/" )

		tree.each { file ->

			if( file.getName( ).toLowerCase( ).indexOf( ".tps" ) >= 0 )
			{
				project.exec {
					commandLine "TexturePacker" , "${ file.getPath( ) }"
					ignoreExitValue = true
				}
			}
		}
	}
}
