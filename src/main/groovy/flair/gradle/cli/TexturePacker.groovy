package flair.gradle.cli

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class TexturePacker extends AbstractCli
{
	@Override
	public void execute( Project project )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {
			executable Os.isFamily( Os.FAMILY_MAC ) ? "/usr/local/bin/texturepacker" : "texturepacker"

			arguments.each {

				println( it )
				args it
			}

			standardOutput = outputStream
		}

		println( ">>result1" + result.exitValue + "--" + result.toString( ) )
		println( ">>result2" + outputStream.toString( ) )
	}
}
