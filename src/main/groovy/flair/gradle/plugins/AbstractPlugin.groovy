package flair.gradle.plugins

import flair.gradle.extensions.IConfigurationExtension
import flair.gradle.extensions.IVariantConfigurationContainerExtension
import flair.gradle.tasks.Group
import flair.gradle.variants.Platform
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
	}

	@Override
	public final Task addTask( String name , Class type )
	{
		Task task = project.tasks.findByName( name )

		task = task ?: project.tasks.create( name , type )

		return task
	}

	@Override
	public final Task addTask( String name , Group group )
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
	public final ExtensionAware addConfigurationExtension( String name , Platform platform , Class<IConfigurationExtension> type )
	{
		addConfigurationExtension( name , platform , type , project )
	}

	@Override
	public
	final ExtensionAware addConfigurationExtension( String name , Platform platform , Class<IConfigurationExtension> type , ExtensionAware parent )
	{
		ExtensionAware extension = parent.extensions.findByName( name ) as ExtensionAware

		return extension ?: parent.extensions.create( name , type , name , project , platform ) as ExtensionAware
	}

	protected IVariantConfigurationContainerExtension getFlairExtension()
	{
		return project.flair as IVariantConfigurationContainerExtension
	}

	protected abstract void addTasks()

	protected abstract void addExtensions()
}
