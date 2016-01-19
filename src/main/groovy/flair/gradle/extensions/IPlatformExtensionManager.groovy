package flair.gradle.extensions

import flair.gradle.variants.Platform
import flair.gradle.variants.Variant
import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformExtensionManager
{
	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getAllProductFlavors()

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getAllBuildTypes()

	public List<Variant> getAllVariants()

	public List<Variant> getAllVariants( Platform platform )

	public Object getFlairProperty( String name )

	public Object getFlairProperty( String name , Platform platform )

	public Object getFlairProperty( String name , Variant variant )

	public Object getFlairProperty( String configurationName , String name )

	public Object getFlairProperty( String configurationName , String name , Platform platform )

	public Object getFlairProperty( String configurationName , String name , Variant variant )
}