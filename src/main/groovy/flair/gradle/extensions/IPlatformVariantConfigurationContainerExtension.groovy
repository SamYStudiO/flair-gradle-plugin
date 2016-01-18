package flair.gradle.extensions

import flair.gradle.variants.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformVariantConfigurationContainerExtension extends IVariantsConfigurationContainerExtension
{
	public IVariantsConfigurationContainerExtension getPlatformContainer( Platform platform )
}