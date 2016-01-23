package flair.gradle.cli

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface ICli
{
	public List<String> getArguments()

	public void addArgument( String arg )

	public void reset()

	public void execute( Project project )
}