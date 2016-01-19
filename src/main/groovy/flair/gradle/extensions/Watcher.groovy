package flair.gradle.extensions

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
	private File root

	private WatchService watcher = FileSystems.getDefault( ).newWatchService( )

	private Map<WatchKey , Path> keys = new HashMap<WatchKey , Path>( )

	private Map<File , Closure> watches = new HashMap<File , Closure>( )

	public Watcher( File root )
	{
		this.root = root
	}

	public watch( File path , Closure closure )
	{
		watches.put( path , closure )
	}

	@Override
	public void run()
	{
		println( "run" + root.toPath( ) )

		registerDir( root.toPath( ) )

		for( ; ; )
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

			println( dir )

			if( dir == null )
			{
				continue
			}

			for( WatchEvent<?> event : key.pollEvents( ) )
			{
				WatchEvent.Kind kind = event.kind( )

				println( kind )

				if( kind == OVERFLOW )
				{
					continue
				}

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

			println( change )

			if( change )
			{
				for( Map.Entry<File , Closure> map : watches )
				{
					println( dir.toFile( ).path + "---------" + map.key.path )
					if( dir.toFile( ).path.startsWith( map.key.path ) ) map.value( )
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
