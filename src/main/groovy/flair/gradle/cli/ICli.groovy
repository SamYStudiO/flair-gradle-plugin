package flair.gradle.cli

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface ICli
{
	List<String> getArguments()

	void addArgument( String arg )

	void reset()

	void execute( Project project )
}