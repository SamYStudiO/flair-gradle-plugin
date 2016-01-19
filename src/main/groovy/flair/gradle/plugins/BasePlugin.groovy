package flair.gradle.plugins

import flair.gradle.extensions.FlairExtension
import flair.gradle.structure.ClassTemplateStructure
import flair.gradle.structure.CommonStructure
import flair.gradle.structure.VariantStructure
import flair.gradle.tasks.Task
import flair.gradle.watcher.executables.GenerateFontClass
import flair.gradle.watcher.executables.GenerateRClass
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class BasePlugin extends AbstractStructurePlugin
{
	@Override
	public void apply( Project project )
	{
		super.apply( project )

		project.afterEvaluate {
			new GenerateRClass( ).execute( project )
			new GenerateFontClass( ).execute( project )
		}
	}

	@Override
	protected void addStructures()
	{
		addStructure( new CommonStructure( ) )
		addStructure( new ClassTemplateStructure( ) )
		addStructure( new VariantStructure( ) )
		addStructure( new CommonStructure( ) )
	}

	@Override
	protected void addTasks()
	{
		addTask( Task.CLEAN.name , Task.CLEAN.type )
		//addTask( Task.ASSEMBLE.name , Group.BUILD )
		//addTask( Task.COMPILE.name , Group.BUILD )

		org.gradle.api.plugins.PluginManager
	}

	@Override
	protected void addExtensions()
	{
		addConfigurationExtension( FlairExtension.NAME , null , FlairExtension )
	}

	@Override
	protected ready()
	{
		flairExtension.watcher.watchPattern( File.separator + "resources" , new GenerateRClass( ) )
		flairExtension.watcher.watchPattern( File.separator + "fonts" , new GenerateFontClass( ) )
	}
}
