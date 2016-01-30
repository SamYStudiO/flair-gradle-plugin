package flair.gradle.plugins

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractMobilePlatformPlugin
{
	public AndroidPlugin()
	{
		platform = Platforms.ANDROID
	}
}
