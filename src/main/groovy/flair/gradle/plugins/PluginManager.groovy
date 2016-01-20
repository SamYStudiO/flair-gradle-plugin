package flair.gradle.plugins

import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PluginManager
{
	public static boolean hasPlugin( Project project , Class type )
	{
		project.plugins.each { plugin ->

			if( plugin.getClass( ) == type ) return true
		}

		return false
	}

	public static boolean hasPlatformPlugin( Project project , Platforms platform )
	{
		boolean hasPlatform = false

		project.plugins.each { plugin ->

			if( plugin instanceof IosPlugin && platform == Platforms.IOS ) hasPlatform = true
			if( plugin instanceof AndroidPlugin && platform == Platforms.ANDROID ) hasPlatform = true
			if( plugin instanceof DesktopPlugin && platform == Platforms.DESKTOP ) hasPlatform = true
		}

		return hasPlatform
	}

	public static List<Platforms> getCurrentPlatforms( Project project )
	{
		List<Platforms> list = new ArrayList<Platforms>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IosPlugin && list.indexOf( Platforms.IOS ) < 0 ) list.add( Platforms.IOS )
			if( plugin instanceof AndroidPlugin && list.indexOf( Platforms.ANDROID ) < 0 ) list.add( Platforms.ANDROID )
			if( plugin instanceof DesktopPlugin && list.indexOf( Platforms.DESKTOP ) < 0 ) list.add( Platforms.DESKTOP )
		}

		return list
	}

	public static boolean hasSinglePlatform( Project project )
	{
		return getCurrentPlatforms( project ).size( ) == 1
	}
}
