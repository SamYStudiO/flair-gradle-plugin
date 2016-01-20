package flair.gradle.extensions

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformVariantConfigurationContainerExtension extends IPlatformConfigurationContainerExtension
{
	public IPlatformConfigurationContainerExtension getPlatformContainer( Platforms platform )
}