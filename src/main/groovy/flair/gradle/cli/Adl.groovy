package flair.gradle.cli

import flair.gradle.dependencies.Sdk
import flair.gradle.utils.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Adl extends AbstractCli
{
	@Override
	String execute( Project project , Platform platform )
	{
		return project.ant.exec( executable: new Sdk( project , platform ).adlPath ) {
			arguments.each {
				println( "\t" + it )
				arg( value: it )
			}
		}
	}
}
