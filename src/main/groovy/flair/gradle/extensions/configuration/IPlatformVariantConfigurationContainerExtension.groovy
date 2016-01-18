package flair.gradle.extensions.configuration

import flair.gradle.variants.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformVariantConfigurationContainerExtension extends IVariantsConfigurationContainerExtension
{
	public IVariantsConfigurationContainerExtension getPlatformContainer( Platform platform )
}