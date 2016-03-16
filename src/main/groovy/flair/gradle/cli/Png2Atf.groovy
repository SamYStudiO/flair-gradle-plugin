package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import flair.gradle.utils.Platform
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Png2Atf extends AbstractCli
{
	@Override
	public String execute( Project project , Platform platform )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {

			commandLine new Sdk( project , platform ).png2AtfPath , "-c" , "e2" , "-n" , "0,0" , "-r"

			arguments.each {

				args it
			}

			ignoreExitValue = true
			standardOutput = outputStream
		}

		//println( outputStream.toString( ) )

		return outputStream.toString( )
	}
}
