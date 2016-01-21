package flair.gradle.extensions

import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IExtensionManager
{
	public IPlatformContainerExtension getPlatformContainer( Platforms platform )

	public NamedDomainObjectContainer<IVariantExtension> getAllActivePlatformProductFlavors()

	public NamedDomainObjectContainer<IVariantExtension> getAllActivePlatformBuildTypes()

	public List<Variant> getAllActivePlatformVariants()

	public List<Variant> getPlatformVariants( Platforms platform )

	public Object getFlairProperty( String name )

	public Object getFlairProperty( Platforms platform , String name )

	public Object getFlairProperty( Variant variant , String name )

	public Object getFlairProperty( String extensionName , String name )

	public Object getFlairProperty( String extensionName , Platforms platform , String name )

	public Object getFlairProperty( String extensionName , Variant variant , String name )
}