package flair.gradle.plugins

import flair.gradle.extensions.configuration.ConfigurationExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractPlatformPlugin
{
	public void addExtensions()
	{
		addConfigurationExtension( Platform.ANDROID.name.toLowerCase( ) , Platform.ANDROID , ConfigurationExtension , flair )
	}
}
