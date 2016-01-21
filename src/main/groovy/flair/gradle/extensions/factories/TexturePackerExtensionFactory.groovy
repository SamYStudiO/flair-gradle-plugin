package flair.gradle.extensions.factories

import flair.gradle.extensions.Extensions
import flair.gradle.extensions.TexturePackerExtension
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class TexturePackerExtensionFactory implements IExtensionFactory<TexturePackerExtension>
{
	@Override
	public TexturePackerExtension create( ExtensionAware parent , Project project )
	{
		return parent.extensions.create( Extensions.TEXTURE_PACKER.name , TexturePackerExtension , Extensions.TEXTURE_PACKER.name , project )
	}
}
