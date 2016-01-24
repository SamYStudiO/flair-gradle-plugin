package flair.gradle.extensions

import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IPlatformContainerExtension extends IPlatformExtension
{
	List<String> getFlavorDimensions()

	void setFlavorDimensions( List<String> flavorDimensions )

	void setFlavorDimensions( String... flavorDimensions )

	NamedDomainObjectContainer<IVariantExtension> getProductFlavors()

	NamedDomainObjectContainer<IVariantExtension> getBuildTypes()

	IVariantExtension getProductFlavor( String name )

	IVariantExtension getBuildType( String name )
}