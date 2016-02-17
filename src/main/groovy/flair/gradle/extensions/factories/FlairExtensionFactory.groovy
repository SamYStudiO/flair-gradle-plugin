package flair.gradle.extensions.factories

import flair.gradle.extensions.Extension
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
		return parent.extensions.create( Extension.FLAIR.name , FlairExtension , Extension.FLAIR.name , project , null )
	}
}
