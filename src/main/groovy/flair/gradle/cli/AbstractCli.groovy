package flair.gradle.cli

import flair.gradle.utils.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
abstract class AbstractCli implements ICli
{
	protected List<String> arguments = new ArrayList<String>( )

	@Override
	List<String> getArguments()
	{
		return arguments.clone( ) as List<String>
	}

	@Override
	void addArgument( String arg )
	{
		String[] a = arg.split( "\\s" )

		for( int i = 0; i < a.size( ); i++ )
		{
			arguments.add( a[ i ] )
		}
	}

	@Override
	void addArguments( List<String> args )
	{
		args.each { addArgument( it ) }
	}

	@Override
	void addArguments( String... args )
	{
		args.each { addArgument( it ) }
	}

	@Override
	void clearArguments()
	{
		arguments = new ArrayList<String>( )
	}

	@Override
	String execute( Project project )
	{
		execute( project , null )
	}

	@Override
	abstract String execute( Project project , Platform platform )
}
