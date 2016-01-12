package flair.gradle.tasks.texturepacker

import flair.gradle.tasks.Group
import flair.gradle.utils.AIRSDKManager
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePacker extends DefaultTask
{
	public TexturePacker()
	{
		group = Group.TEXTURE_PACKER.name
		description = ""
	}

	@TaskAction
	public void publishAtlases()
	{
		String moduleName = project.flair.moduleName
		boolean generateATFTexturesFromAtlases = project.flair.generateATFTexturesFromAtlases

		String toATF = ""
		FileTree tree = project.fileTree( "${ moduleName }/src/main/" )

		tree.each { file ->

			if( file.getName( ).toLowerCase( ).indexOf( ".tps" ) >= 0 )
			{
				ByteArrayOutputStream output = new ByteArrayOutputStream( )

				project.exec {
					executable Os.isFamily( Os.FAMILY_WINDOWS ) ? "texturepacker" : "/usr/local/bin/texturepacker"
					args file.getPath( )
					ignoreExitValue = true

					standardOutput = output
				}

				if( output.toString( ).indexOf( "Output files are newer than the input files, nothing to do" ) < 0 ) toATF += file.getName( ).toLowerCase( ).split( "\\." )[ 0 ]
			}
		}

		if( generateATFTexturesFromAtlases )
		{
			String png2atf = "${ AIRSDKManager.getPath( project ) }/atftools/png2atf"

			tree = project.fileTree( "${ moduleName }/src/main/resources" ) {
				include "drawable*/**/*.png"
			}

			tree.each { file ->

				if( toATF.indexOf( file.getName( ).toLowerCase( ).replaceAll( "[0-9]+\\." , "\\." ).split( "\\." )[ 0 ] ) >= 0 )
				{
					String input = file.getAbsolutePath( )
					String output = input.replaceAll( "\\.png" , "\\.atf" )

					project.exec {
						commandLine "${ png2atf }" , "-c" , "e2" , "-n" , "0,0" , "-r" , "-i" , input , "-o" , output
						ignoreExitValue = true
					}
				}
			}
		}
	}
}
