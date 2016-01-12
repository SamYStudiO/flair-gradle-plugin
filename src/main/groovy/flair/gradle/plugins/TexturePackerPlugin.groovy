package flair.gradle.plugins

import flair.gradle.extensions.TexturePackerExtension
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractPlugin
{
	public void addTasks()
	{
		addTask( Task.PUBLISH_ATLASES.name , Task.PUBLISH_ATLASES.type )
	}

	public void addExtensions()
	{
		addExtension( TexturePackerExtension.NAME , TexturePackerExtension , flair )
	}
}
