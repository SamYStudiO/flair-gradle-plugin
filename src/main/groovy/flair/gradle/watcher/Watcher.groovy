package flair.gradle.watcher

import org.gradle.api.Project

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes
import java.util.concurrent.TimeUnit

import static java.nio.file.LinkOption.NOFOLLOW_LINKS
import static java.nio.file.StandardWatchEventKinds.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Watcher extends Thread
{
	private Project project

	private File root

	private WatchService watcher = FileSystems.getDefault( ).newWatchService( )

	private Map<WatchKey , Path> keys = new HashMap<WatchKey , Path>( )

	private Map<File , IWatcherExecutable> watchDirectories = new HashMap<File , IWatcherExecutable>( )

	private Map<String , IWatcherExecutable> watchPatterns = new HashMap<String , IWatcherExecutable>( )

	public Watcher( Project project )
	{
		this( project , project.rootDir )
	}

	public Watcher( Project project , File root )
	{
		this.root = root
		this.project = project
	}

	public watchDirectory( File path , IWatcherExecutable executable )
	{
		watchDirectories.put( path , executable )
	}

	public watchPattern( String pattern , IWatcherExecutable executable )
	{
		watchPatterns.put( pattern , executable )
	}

	@Override
	public void run()
	{
		registerDir( root.toPath( ) )

		while( true )
		{
			boolean change = false

			WatchKey key

			try
			{
				//key = watcher.take( )
				key = watcher.poll( 25 , TimeUnit.MILLISECONDS )
			}
			catch( InterruptedException x )
			{
				return
			}

			Path dir = keys.get( key )

			if( dir == null ) continue

			for( WatchEvent<?> event : key.pollEvents( ) )
			{
				WatchEvent.Kind kind = event.kind( )

				if( kind == OVERFLOW ) continue

				@SuppressWarnings( "unchecked" )
				WatchEvent<Path> ev = ( WatchEvent<Path> ) event
				Path name = ev.context( )
				Path child = dir.resolve( name )

				if( kind == ENTRY_CREATE )
				{
					try
					{
						if( Files.isDirectory( child , NOFOLLOW_LINKS ) )
						{
							registerDir( child )
						}
					}
					catch( IOException x )
					{}
				}

				change = true
			}

			if( change )
			{
				for( Map.Entry<File , IWatcherExecutable> map : watchDirectories )
				{
					if( dir.toFile( ).path.startsWith( map.key.path ) ) map.value.execute( project )
				}

				for( Map.Entry<String , IWatcherExecutable> map : watchPatterns )
				{
					if( dir.toFile( ).path.contains( map.key ) ) map.value.execute( project )
				}
			}

			boolean valid = key.reset( )

			if( !valid )
			{
				keys.remove( key )

				if( keys.isEmpty( ) ) break
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
}
