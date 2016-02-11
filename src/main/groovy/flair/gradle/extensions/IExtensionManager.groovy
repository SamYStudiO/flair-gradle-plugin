package flair.gradle.extensions

import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IExtensionManager
{
	IPlatformContainerExtension getPlatformContainer( Platforms platform )

	List<String> getAllActivePlatformProductFlavors()

	List<String> getAllActivePlatformBuildTypes()

	List<Variant> getAllActivePlatformVariants()

	List<Variant> getPlatformVariants( Platforms platform )

	Object getFlairProperty( FlairProperties property )

	Object getFlairProperty( Platforms platform , FlairProperties property )

	Object getFlairProperty( Variant variant , FlairProperties property )

	Object getFlairProperty( String extensionName , FlairProperties property )

	Object getFlairProperty( String extensionName , Platforms platform , FlairProperties property )

	Object getFlairProperty( String extensionName , Variant variant , FlairProperties property )
}