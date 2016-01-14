package flair.gradle.plugins

import flair.gradle.extensions.TexturePackerExtension
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractPlugin
{
	@Override
	public void addTasks()
	{
		addTask( Task.PUBLISH_ATLASES.name , Task.PUBLISH_ATLASES.type )
	}

	@Override
	public void addExtensions()
	{
		addExtension( TexturePackerExtension.NAME , TexturePackerExtension , flair )
	}
}
