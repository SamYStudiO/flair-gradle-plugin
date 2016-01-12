package flair.gradle.plugins

import flair.gradle.extensions.TexturePackerExtension
import flair.gradle.tasks.Tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractPlugin
{
	public void addTasks()
	{
		addTask( Tasks.PUBLISH_ATLASES.name , Tasks.PUBLISH_ATLASES.type )
	}

	public void addExtensions()
	{
		addExtension( TexturePackerExtension.NAME , TexturePackerExtension , flair )
	}
}
