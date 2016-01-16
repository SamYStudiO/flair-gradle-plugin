package flair.gradle.extensions.configuration

import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantConfigurationContainerExtension extends IConfigurationContainerExtension
{
	public NamedDomainObjectContainer<IConfigurationContainerExtension> getProductFlavors()

	public NamedDomainObjectContainer<IConfigurationContainerExtension> getBuildTypes()

	public IConfigurationContainerExtension getProductFlavor( String name )

	public IConfigurationContainerExtension getBuildType( String name )
}