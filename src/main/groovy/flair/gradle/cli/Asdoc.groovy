package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Asdoc extends AbstractCli
{
	@Override
	public String execute( Project project )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {
			executable new Sdk( project ).asdocPath

			arguments.each { args it }

			standardOutput = outputStream
		}

		println( outputStream.toString( ) )

		return  outputStream.toString( )
	}
}
