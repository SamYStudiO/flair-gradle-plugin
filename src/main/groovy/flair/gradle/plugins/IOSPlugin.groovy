package flair.gradle.plugins

import flair.gradle.extensions.configuration.ConfigurationExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSPlugin extends AbstractPlatformPlugin
{
	public void addExtensions()
	{
		addConfigurationExtension( Platform.IOS.name.toLowerCase( ) , Platform.IOS , ConfigurationExtension , flair )
	}
}
