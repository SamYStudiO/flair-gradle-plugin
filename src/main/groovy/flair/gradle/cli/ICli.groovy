package flair.gradle.cli

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface ICli
{
	List<String> getArguments()

	void addArgument( String arg )

	void addArguments( String... args )

	void addArguments( List<String> args )

	void clearArguments()

	String execute( Project project )
}