package flair.gradle.plugins

import flair.gradle.extensions.IConfigurationExtension
import flair.gradle.extensions.IPlatformExtensionManager
import flair.gradle.tasks.Groups
import flair.gradle.variants.Platforms
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlugin implements IPlugin
{
	protected Project project

	@Override
	public void apply( Project project )
	{
		this.project = project

		addTasks( )
		addExtensions( )
		ready( )
	}

	@Override
	public final Task addTask( String name , Class type )
	{
		Task task = project.tasks.findByName( name )

		task = task ?: project.tasks.create( name , type )

		return task
	}

	@Override
	public final Task addTask( String name , Groups group )
	{
		Task task = project.tasks.findByName( name )

		task = task ?: project.tasks.create( name )

		task.group = group

		return task
	}

	@Override
	public final ExtensionAware addExtension( String name , Class type )
	{
		addExtension( name , type , project )
	}

	@Override
	public final ExtensionAware addExtension( String name , Class type , ExtensionAware parent )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type ) as ExtensionAware
	}

	@Override
	public final ExtensionAware addConfigurationExtension( String name , Platforms platform , Class<IConfigurationExtension> type )
	{
		addConfigurationExtension( name , platform , type , project )
	}

	@Override
	public
	final ExtensionAware addConfigurationExtension( String name , Platforms platform , Class<IConfigurationExtension> type , ExtensionAware parent )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type , name , project , platform ) as ExtensionAware
	}

	protected IPlatformExtensionManager getFlairExtension()
	{
		return project.flair as IPlatformExtensionManager
	}

	protected void ready()
	{
	}

	protected abstract void addTasks()

	protected abstract void addExtensions()
}
