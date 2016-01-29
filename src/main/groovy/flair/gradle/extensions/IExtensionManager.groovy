package flair.gradle.extensions

import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IExtensionManager
{
	IPlatformContainerExtension getPlatformContainer( Platforms platform )

	List<IVariantExtension> getAllActivePlatformProductFlavors()

	List<IVariantExtension> getAllActivePlatformBuildTypes()

	List<Variant> getAllActivePlatformVariants()

	List<Variant> getPlatformVariants( Platforms platform )

	Object getFlairProperty( String name )

	Object getFlairProperty( Platforms platform , String name )

	Object getFlairProperty( Variant variant , String name )

	Object getFlairProperty( String extensionName , String name )

	Object getFlairProperty( String extensionName , Platforms platform , String name )

	Object getFlairProperty( String extensionName , Variant variant , String name )
}