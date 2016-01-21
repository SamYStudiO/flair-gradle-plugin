package flair.gradle.extensions.factories

import flair.gradle.extensions.Extensions
import flair.gradle.extensions.FlairExtension
import flair.gradle.extensions.IPlatformContainerExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtensionFactory implements IExtensionFactory<IPlatformContainerExtension>
{
	@Override
	public IPlatformContainerExtension create( ExtensionAware parent , Project project )
	{
		return parent.extensions.create( Extensions.FLAIR.name , FlairExtension , Extensions.FLAIR.name , project , null )
	}
}
