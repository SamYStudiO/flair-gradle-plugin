package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Adl extends AbstractCli
{
	@Override
	public String execute( Project project )
	{
		return project.ant.exec( executable: new Sdk( project ).adlPath ) {
			arguments.each {
				println( "\t" + it )
				arg( value: it )
			}
		}
	}
}
