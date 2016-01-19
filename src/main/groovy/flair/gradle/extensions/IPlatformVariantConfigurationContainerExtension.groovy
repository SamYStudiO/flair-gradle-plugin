package flair.gradle.extensions

import flair.gradle.variants.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformVariantConfigurationContainerExtension extends IPlatformConfigurationContainerExtension
{
	public IPlatformConfigurationContainerExtension getPlatformContainer( Platform platform )
}