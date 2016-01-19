package flair.gradle.plugins

import flair.gradle.variants.Platform
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

	public static boolean hasPlatformPlugin( Project project , Platform platform )
	{
		boolean hasPlatform = false

		project.plugins.each { plugin ->

			if( plugin instanceof IosPlugin && platform == Platform.IOS ) hasPlatform = true
			if( plugin instanceof AndroidPlugin && platform == Platform.ANDROID ) hasPlatform = true
			if( plugin instanceof DesktopPlugin && platform == Platform.DESKTOP ) hasPlatform = true
		}

		return hasPlatform
	}

	public static List<Platform> getCurrentPlatforms( Project project )
	{
		List<Platform> list = new ArrayList<Platform>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IosPlugin && list.indexOf( Platform.IOS ) < 0 ) list.add( Platform.IOS )
			if( plugin instanceof AndroidPlugin && list.indexOf( Platform.ANDROID ) < 0 ) list.add( Platform.ANDROID )
			if( plugin instanceof DesktopPlugin && list.indexOf( Platform.DESKTOP ) < 0 ) list.add( Platform.DESKTOP )
		}

		return list
	}

	public static boolean hasSinglePlatform( Project project )
	{
		return getCurrentPlatforms( project ).size( ) == 1
	}
}
