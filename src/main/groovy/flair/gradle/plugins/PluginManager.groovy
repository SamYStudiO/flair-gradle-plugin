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

			if( plugin instanceof IPlatformPlugin && plugin.platform == platform ) hasPlatform = true
		}

		return hasPlatform
	}

	public static List<Platforms> getCurrentPlatforms( Project project )
	{
		List<Platforms> list = new ArrayList<Platforms>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IPlatformPlugin && list.indexOf( plugin.platform ) < 0 ) list.add( plugin.platform )
		}

		return list
	}

	public static boolean hasSinglePlatform( Project project )
	{
		return getCurrentPlatforms( project ).size( ) == 1
	}
}
