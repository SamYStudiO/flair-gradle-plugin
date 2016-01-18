package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformVariantConfigurationContainerExtension extends IVariantsConfigurationContainerExtension
{
	public IVariantsConfigurationContainerExtension getPlatformContainer( Platform platform )
}