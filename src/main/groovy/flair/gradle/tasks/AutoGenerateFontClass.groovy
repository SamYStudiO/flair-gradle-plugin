package flair.gradle.tasks

import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.tasks.Group
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

import static java.nio.file.LinkOption.NOFOLLOW_LINKS
import static java.nio.file.StandardWatchEventKinds.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AutoGenerateFontClass extends DefaultTask
{
	private WatchService watcher

	private Map<WatchKey , Path> keys

	@SuppressWarnings( "unchecked" )
	static <T> WatchEvent<T> cast( WatchEvent<?> event )
	{
		return ( WatchEvent<T> ) event;
	}

	public AutoGenerateFontClass()
	{
		group = Group.GENERATED.name
		description = ""
	}

	@TaskAction
	public void watchFonts()
	{
		runGenerateFontClassTask( )

		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		watcher = FileSystems.getDefault( ).newWatchService( )
		keys = new HashMap<WatchKey , Path>( )

		registerDir( project.file( "${ moduleName }/src/main/fonts" ).toPath( ) );


		for( ; ; )
		{
			boolean change = false

			WatchKey key
			try
			{
				key = watcher.take( )
			}
			catch( InterruptedException x )
			{
				return
			}

			Path dir = keys.get( key )
			if( dir == null )
			{
				continue;
			}

			for( WatchEvent<?> event : key.pollEvents( ) )
			{
				WatchEvent.Kind kind = event.kind( )

				if( kind == OVERFLOW )
				{
					continue;
				}

				WatchEvent<Path> ev = cast( event )
				Path name = ev.context( )
				Path child = dir.resolve( name )

				if( kind == ENTRY_CREATE )
				{
					try
					{
						if( Files.isDirectory( child , NOFOLLOW_LINKS ) )
						{
							registerDir( child );
						}
					}
					catch( IOException x )
					{}
				}

				change = true
			}

			if( change ) runGenerateFontClassTask( )

			boolean valid = key.reset( )

			if( !valid )
			{
				keys.remove( key )

				if( keys.isEmpty( ) )
				{
					break
				}
			}
		}
	}

	private void registerDir( Path root )
	{
		Files.walkFileTree( root , new SimpleFileVisitor<Path>( )
		{
			@Override
			public FileVisitResult preVisitDirectory( Path dir , BasicFileAttributes attrs ) throws IOException
			{
				WatchKey key = dir.register( watcher , ENTRY_CREATE , ENTRY_DELETE , ENTRY_MODIFY )

				keys.put( key , dir )

				return FileVisitResult.CONTINUE
			}
		} )
	}

	private void runGenerateFontClassTask()
	{
		ProjectConnection connection = GradleConnector.newConnector( )
				.forProjectDirectory( project.projectDir )
				.connect( )

		connection.newBuild( )
				.forTasks( "generateFontClass" )
				.run( )

		connection.close( )
	}
}
