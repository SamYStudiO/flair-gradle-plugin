package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantConfigurationContainerExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSPlugin extends AbstractPlatformPlugin
{
	@Override
	public void addExtensions()
	{
		addConfigurationExtension( Platform.IOS.name.toLowerCase( ) , Platform.IOS , VariantConfigurationContainerExtension , flair )
	}
}
