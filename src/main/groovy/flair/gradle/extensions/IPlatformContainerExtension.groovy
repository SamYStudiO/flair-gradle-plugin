package flair.gradle.extensions

import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformContainerExtension extends IPlatformExtension
{
	List<String> getFlavorDimensions()

	void flavorDimensions( List<String> flavorDimensions )

	void flavorDimensions( String... flavorDimensions )

	NamedDomainObjectContainer<IVariantExtension> getProductFlavors()

	NamedDomainObjectContainer<IVariantExtension> getBuildTypes()

	IVariantExtension getProductFlavor( String name )

	IVariantExtension getBuildType( String name )
}