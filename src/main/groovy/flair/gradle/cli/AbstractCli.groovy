package flair.gradle.cli

import flair.gradle.directoryWatcher.IWatcherAction
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractCli implements ICli , IWatcherAction
{
	private List<String> arguments = new ArrayList<String>( )

	@Override
	public List<String> getArguments()
	{
		return arguments.clone( ) as List<String>
	}

	@Override
	public void addArgument( String arg )
	{
		arguments.add( arg )
	}

	@Override
	public void reset()
	{
		arguments = new ArrayList<String>( )
	}

	@Override
	public abstract void execute( Project project )
}
