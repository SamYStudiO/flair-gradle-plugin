package flair.gradle.cli

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractCli implements ICli
{
	protected List<String> arguments = new ArrayList<String>( )

	@Override
	public List<String> getArguments()
	{
		return arguments.clone( ) as List<String>
	}

	@Override
	public void addArgument( String arg )
	{
		String[] a = arg.split( "\\s" )

		for( int i = 0; i < a.size( ); i++ ) { arguments.add( a[ i ] ) }
	}

	@Override
	public void addArguments( List<String> args )
	{
		args.each { addArgument( it ) }
	}

	@Override
	public void addArguments( String... args )
	{
		args.each { addArgument( it ) }
	}

	@Override
	public void clearArguments()
	{
		arguments = new ArrayList<String>( )
	}

	@Override
	public abstract String execute( Project project )
}
