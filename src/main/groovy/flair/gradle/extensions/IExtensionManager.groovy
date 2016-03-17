package flair.gradle.extensions

import flair.gradle.utils.Platform
import flair.gradle.utils.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IExtensionManager
{
	IPlatformContainerExtension getPlatformContainer( Platform platform )

	List<String> getAllActivePlatformProductFlavors()

	List<String> getAllActivePlatformBuildTypes()

	List<Variant> getAllActivePlatformVariants()

	List<Variant> getPlatformVariants( Platform platform )

	Object getFlairProperty( FlairProperty property )

	Object getFlairProperty( Platform platform , FlairProperty property )

	Object getFlairProperty( Variant variant , FlairProperty property )

	Object getFlairProperty( String extensionName , FlairProperty property )

	Object getFlairProperty( String extensionName , Platform platform , FlairProperty property )

	Object getFlairProperty( String extensionName , Variant variant , FlairProperty property )
}