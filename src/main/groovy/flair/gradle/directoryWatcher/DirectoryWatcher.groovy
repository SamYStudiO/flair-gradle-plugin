package flair.gradle.directoryWatcher

import org.gradle.api.Project

import java.nio.file.*
import java.nio.file.attribute.BasicFileAttributes

import static java.nio.file.LinkOption.NOFOLLOW_LINKS
import static java.nio.file.StandardWatchEventKinds.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class DirectoryWatcher implements Runnable
{
	private Project project

	private File root

	private WatchService watcher = FileSystems.getDefault( ).newWatchService( )

	private Map<WatchKey , Path> keys = new HashMap<WatchKey , Path>( )

	private Map<File , IWatcherAction> watchDirectories = new HashMap<File , IWatcherAction>( )

	private Map<String , IWatcherAction> watchPatterns = new HashMap<String , IWatcherAction>( )

	public DirectoryWatcher( Project project )
	{
		this( project , project.rootDir )
	}

	public DirectoryWatcher( Project project , File root )
	{
		this.root = root
		this.project = project
	}

	public watch( File path , IWatcherAction action )
	{
		watchDirectories.put( path , action )
	}

	public watch( String pattern , IWatcherAction action )
	{
		watchPatterns.put( pattern , action )
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
				key = watcher.take( )
				//key = watcher.poll( 25 , TimeUnit.MILLISECONDS )
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

			boolean valid = key.reset( )
			boolean needToBreak = false

			if( !valid )
			{
				keys.remove( key )

				if( keys.isEmpty( ) ) needToBreak = true
			}

			if( change )
			{
				Thread.sleep( 1000 )

				for( Map.Entry<File , IWatcherAction> fileMap : watchDirectories )
				{
					if( dir.toFile( ).path.startsWith( fileMap.key.path ) ) fileMap.value.execute( project )
				}

				for( Map.Entry<String , IWatcherAction> stringMap : watchPatterns )
				{
					if( dir.toFile( ).path.contains( stringMap.key ) ) stringMap.value.execute( project )
				}
			}

			if( needToBreak ) break
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
