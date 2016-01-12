package flair.gradle.subPlugins.platforms.android

import flair.gradle.AbstractPlugin
import flair.gradle.FlairExtension
import flair.gradle.subPlugins.texturepacker.extensions.TexturePackerExtension
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AndroidPlugin extends AbstractPlugin
{
	public void addTasks()
	{
	}

	public void addExtensions()
	{
		addExtension( TexturePackerExtension.NAME , FlairExtension , ( ExtensionAware ) project.getExtensions( ).getByName( FlairExtension.NAME ) )
	}
}
