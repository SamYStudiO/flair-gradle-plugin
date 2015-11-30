package com.flair.tasks

import com.flair.utils.SDKManager
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

		String toATF = ""

		tree.each { file ->

			if( file.getName( ).toLowerCase( ).indexOf( ".tps" ) >= 0 )
			{
				ByteArrayOutputStream output = new ByteArrayOutputStream( )

				project.exec {
					commandLine "TexturePacker" , "${ file.getPath( ) }"
					ignoreExitValue = true

					standardOutput = output
				}

				println( ">>>>ee" + output.toString( ) + "<<<" )

				if( output.toString( ).indexOf( "Output files are newer than the input files, nothing to do" ) < 0 ) toATF += file.getName( ).toLowerCase( ).split( "\\." )[ 0 ]
			}
		}

		println( "toATF>" + toATF )

		String png2atf = new File( SDKManager.getPath( project ) + File.separator + "atftools" + File.separator + "png2atf.exe" ).getPath( )

		tree = project.fileTree( "${ moduleName }/src/main/resources" ) {
			include "**/drawable*/**/*.png"
		}

		tree.each { file ->

			println( ">" + file.getName( ) )

			if( toATF.indexOf( file.getName( ).toLowerCase( ).replaceAll( "[0-9]+\\." , "\\." ).split( "\\." )[ 0 ] ) >= 0 )
			{
				println( "do" + file.getName( ) )
				String input = file.getAbsolutePath( )
				String output = input.replaceAll( "\\.png" , "\\.atf" )
				//output = output.replaceAll( "-[dpihlmx]{4,7}" , "\$0-etc2" )

				File directory = new File( output ).getParentFile(  )

				println( ">>>>>>> " + directory.getPath(  ) )
				if( !directory.exists( ) ) project.mkdir( directory )

				/*File xml = project.file( input.replaceAll( "\\.png" , "\\.xml" ) )
				if( xml.exists( ) )
				{
					project.copy {
						from xml
						into directory
					}
				}*/

				println( png2atf )
				println( input )
				println( output )
				project.exec {
					commandLine "${ png2atf }" , "-c" , "e2" , "-n" , "0,0" , "-r" , "-i" , "${ input }" , "-o" , "${ output }"
					ignoreExitValue = true
				}
			}
		}
	}
}
