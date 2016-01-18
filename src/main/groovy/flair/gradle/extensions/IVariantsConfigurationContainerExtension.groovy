package flair.gradle.extensions

import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantsConfigurationContainerExtension extends IConfigurationContainerExtension
{
	public List<String> getFlavorDimensions()

	public void setFlavorDimensions( List<String> flavorDimensions )

	public void setFlavorDimensions( String... flavorDimensions )

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getProductFlavors()

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getBuildTypes()

	public IVariantConfigurationContainerExtension getProductFlavor( String name )

	public IVariantConfigurationContainerExtension getBuildType( String name )
}