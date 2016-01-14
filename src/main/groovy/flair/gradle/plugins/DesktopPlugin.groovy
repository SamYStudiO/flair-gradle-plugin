package flair.gradle.plugins

import flair.gradle.extensions.configuration.ConfigurationExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlatformPlugin
{
	public void addExtensions()
	{
		addConfigurationExtension( Platform.DESKTOP.name.toLowerCase( ) , Platform.DESKTOP , ConfigurationExtension , flair )
	}
}
