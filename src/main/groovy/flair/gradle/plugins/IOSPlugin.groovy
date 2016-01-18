package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantsConfigurationContainerExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSPlugin extends AbstractPlatformPlugin
{
	@Override
	public void addExtensions()
	{
		addConfigurationExtension( Platform.IOS.name.toLowerCase( ) , Platform.IOS , VariantsConfigurationContainerExtension , flair )
	}
}
