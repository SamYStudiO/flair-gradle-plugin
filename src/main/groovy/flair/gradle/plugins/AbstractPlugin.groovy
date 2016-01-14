package flair.gradle.plugins

import flair.gradle.extensions.FlairExtension
import flair.gradle.platforms.Platform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlugin implements Plugin<Project>
{
	protected Project project

	protected ExtensionAware flair

	@Override
	public void apply( Project project )
	{
		this.project = project

		flair = addConfigurationExtension( FlairExtension.NAME , null , FlairExtension )

		addTasks( )
		addExtensions( )
	}

	public abstract void addTasks()

	public abstract void addExtensions()

	public Task addTask( String name , Class type )
	{
		Task task = project.tasks.findByName( name )

		return task ?: project.tasks.create( name , type )
	}

	protected ExtensionAware addExtension( String name , Class type , ExtensionAware parent = project )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type ) as ExtensionAware
	}

	protected ExtensionAware addConfigurationExtension( String name , Platform platform , Class type , ExtensionAware parent = project )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type , name , project , platform ) as ExtensionAware
	}
}
