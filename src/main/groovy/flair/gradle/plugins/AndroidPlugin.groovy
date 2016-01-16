package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantConfigurationContainerExtension
import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractPlatformPlugin
{
	@Override
	public void addExtensions()
	{
		addConfigurationExtension( Platform.ANDROID.name.toLowerCase( ) , Platform.ANDROID , VariantConfigurationContainerExtension , flair )
	}
}
