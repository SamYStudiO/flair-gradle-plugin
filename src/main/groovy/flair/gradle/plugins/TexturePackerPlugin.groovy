package flair.gradle.plugins

import flair.gradle.extensions.TexturePackerExtension
import flair.gradle.structure.AtlasesStructure
import flair.gradle.tasks.Task
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractStructurePlugin
{
	@Override
	public void apply( Project project )
	{
		project.apply( plugin: BasePlugin )

		super.apply( project )
	}

	@Override
	public void addTasks()
	{
		addTask( Task.PUBLISH_ATLASES.name , Task.PUBLISH_ATLASES.type )
	}

	@Override
	public void addExtensions()
	{
		addExtension( TexturePackerExtension.NAME , TexturePackerExtension , flairExtension as ExtensionAware )
	}

	@Override
	protected void addStructures()
	{
		addStructure( new AtlasesStructure( ) )
	}
}
