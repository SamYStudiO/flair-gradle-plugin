package flair.gradle.plugins

import flair.gradle.extensions.FlairExtension
import flair.gradle.platforms.Platform
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlugin implements IPlugin
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

	@Override
	public abstract void addTasks()

	@Override
	public abstract void addExtensions()

	@Override
	public Task addTask( String name , Class type )
	{
		Task task = project.tasks.findByName( name )

		return task ?: project.tasks.create( name , type )
	}

	@Override
	public ExtensionAware addExtension( String name , Class type )
	{
		addExtension( name , type , project )
	}

	@Override
	public ExtensionAware addExtension( String name , Class type , ExtensionAware parent )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type ) as ExtensionAware
	}

	@Override
	public ExtensionAware addConfigurationExtension( String name , Platform platform , Class type )
	{
		addConfigurationExtension( name , platform , type , project )
	}

	@Override
	public ExtensionAware addConfigurationExtension( String name , Platform platform , Class type , ExtensionAware parent )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type , name , project , platform ) as ExtensionAware
	}
}
