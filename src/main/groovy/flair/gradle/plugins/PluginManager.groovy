package flair.gradle.plugins

import flair.gradle.utils.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PluginManager
{
	static boolean hasPlugin( Project project , Class type )
	{
		boolean hasPlugin = false

		project.plugins.each { plugin -> if( plugin.getClass( ) == type ) hasPlugin = true }

		return hasPlugin
	}

	static boolean hasPlatformPlugin( Project project , Platform platform )
	{
		boolean hasPlatform = false

		project.plugins.each { plugin ->

			if( plugin instanceof IPlatformPlugin && plugin.platform == platform ) hasPlatform = true
		}

		return hasPlatform
	}

	static List<Platform> getCurrentPlatforms( Project project )
	{
		List<Platform> list = new ArrayList<Platform>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IPlatformPlugin && list.indexOf( plugin.platform ) < 0 ) list.add( plugin.platform )
		}

		return list
	}

	static boolean hasSinglePlatform( Project project )
	{
		return getCurrentPlatforms( project ).size( ) == 1
	}
}
