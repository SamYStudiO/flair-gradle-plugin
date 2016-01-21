package flair.gradle.extensions

import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformContainerExtension extends IPlatformExtension
{
	public List<String> getFlavorDimensions()

	public void setFlavorDimensions( List<String> flavorDimensions )

	public void setFlavorDimensions( String... flavorDimensions )

	public NamedDomainObjectContainer<IVariantExtension> getProductFlavors()

	public NamedDomainObjectContainer<IVariantExtension> getBuildTypes()

	public IVariantExtension getProductFlavor( String name )

	public IVariantExtension getBuildType( String name )
}