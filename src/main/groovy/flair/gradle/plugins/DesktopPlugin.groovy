package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantsConfigurationContainerExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlatformPlugin
{
	@Override
	public void addExtensions()
	{
		addConfigurationExtension( Platform.DESKTOP.name.toLowerCase( ) , Platform.DESKTOP , VariantsConfigurationContainerExtension , flair )
	}
}
