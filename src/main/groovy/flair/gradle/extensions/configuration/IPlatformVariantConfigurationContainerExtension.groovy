package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformVariantConfigurationContainerExtension extends IVariantConfigurationContainerExtension
{
	public IVariantConfigurationContainerExtension getPlatformContainer( Platform platform )
}