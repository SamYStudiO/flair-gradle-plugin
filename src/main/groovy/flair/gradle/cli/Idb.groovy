package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import flair.gradle.utils.Platform
import org.gradle.api.Project
import org.gradle.process.ExecResult

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Idb extends AbstractCli
{
	@Override
	public String execute( Project project , Platform platform )
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

		ExecResult result = project.exec {

			executable new Sdk( project , platform ).idbPath

			arguments.each {

				println( "\t" + it )
				args it
			}

			standardOutput = outputStream
		}

		println( outputStream.toString( ) )

		return outputStream.toString( )
	}
}
