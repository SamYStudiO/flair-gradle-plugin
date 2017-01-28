package flair.gradle.extensions.factories

import flair.gradle.extensions.IPlatformContainerExtension
import flair.gradle.extensions.PlatformContainerExtension
import flair.gradle.utils.Platform
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PlatformExtensionFactory implements IExtensionFactory<IPlatformContainerExtension>
{
	private Platform platform

	PlatformExtensionFactory( Platform platform )
	{
		this.platform = platform
	}

	@Override
	IPlatformContainerExtension create( ExtensionAware parent , Project project )
	{
		return parent.extensions.create( platform.name.toLowerCase( ) , PlatformContainerExtension , platform.name.toLowerCase( ) , project , platform )
	}
}
