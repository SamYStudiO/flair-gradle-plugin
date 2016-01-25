package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Png2Atf extends AbstractCli
{
	@Override
	public void execute( Project project )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {

			commandLine new Sdk( project ).png2AtfPath , "-c" , "e2" , "-n" , "0,0" , "-r"

			arguments.each {

				println( it )
				args it
			}

			ignoreExitValue = true
			standardOutput = outputStream
		}

		println( outputStream.toString( ) )
	}
}
