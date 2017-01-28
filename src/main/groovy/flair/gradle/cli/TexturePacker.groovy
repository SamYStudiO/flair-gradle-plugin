package flair.gradle.cli

import flair.gradle.utils.Platform
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePacker extends AbstractCli
{
	@Override
	String execute( Project project , Platform platform )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {

			executable Os.isFamily( Os.FAMILY_MAC ) ? "/usr/local/bin/texturepacker" : "texturepacker"

			arguments.each {

				println( "\t" + it )
				args it
			}

			ignoreExitValue = true
			standardOutput = outputStream
		}

		println( outputStream.toString( ) )

		return outputStream.toString( )
	}
}
