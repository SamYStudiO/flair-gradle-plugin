package flair.gradle.extensions.configuration

import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantsConfigurationContainerExtension extends IConfigurationContainerExtension
{
	public List<String> getFlavorDimensions()

	public void setFlavorDimensions( List<String> value )

	public void setFlavorDimensions( String... values )

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getProductFlavors()

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getBuildTypes()

	public IVariantConfigurationContainerExtension getProductFlavor( String name )

	public IVariantConfigurationContainerExtension getBuildType( String name )
}