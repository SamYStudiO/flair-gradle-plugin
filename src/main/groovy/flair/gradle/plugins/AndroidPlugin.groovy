package flair.gradle.plugins

import flair.gradle.utils.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AndroidPlugin extends AbstractMobilePlatformPlugin
{
	AndroidPlugin()
	{
		platform = Platform.ANDROID
	}
}
