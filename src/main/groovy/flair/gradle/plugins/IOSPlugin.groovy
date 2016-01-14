package flair.gradle.plugins

import flair.gradle.extensions.configuration.PlatformConfigurationExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSPlugin extends AbstractPlatformPlugin
{
	@Override
	public void addExtensions()
	{
		addConfigurationExtension( Platform.IOS.name.toLowerCase( ) , Platform.IOS , PlatformConfigurationExtension , flair )
	}
}
