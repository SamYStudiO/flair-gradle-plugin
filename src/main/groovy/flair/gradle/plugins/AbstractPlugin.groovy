package flair.gradle.plugins

import flair.gradle.extensions.FlairExtension
import flair.gradle.extensions.configuration.factory.BuildTypeFactory
import flair.gradle.extensions.configuration.factory.ProductFlavorFactory
import flair.gradle.extensions.configuration.variants.BuildTypeExtension
import flair.gradle.extensions.configuration.variants.ProductFlavorExtension
import org.gradle.api.NamedDomainObjectContainer
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

		flair = ( ExtensionAware ) project.getExtensions( ).findByName( FlairExtension.NAME )

		if( !flair ) flair = addConfigurationExtension( FlairExtension.NAME , FlairExtension )

		addTasks( )
		addExtensions( )
	}

	public abstract void addTasks()

	public abstract void addExtensions()

	public Task addTask( String name , Class type )
	{
		Task task = project.tasks.findByName( name )

		return task ? task : project.tasks.create( name , type )
	}

	protected ExtensionAware addExtension( String name , Class type , ExtensionAware parent = project )
	{
		return parent.extensions.create( name , type ) as ExtensionAware
	}

	protected ExtensionAware addConfigurationExtension( String name , Class type , ExtensionAware parent = project )
	{
		NamedDomainObjectContainer<BuildTypeExtension> buildTypeContainer = project.container( BuildTypeExtension , new BuildTypeFactory( project ) )
		NamedDomainObjectContainer<ProductFlavorExtension> productFlavorContainer = project.container( ProductFlavorExtension , new ProductFlavorFactory( project ) )
		return parent.extensions.create( name , type , name , project , buildTypeContainer , productFlavorContainer ) as ExtensionAware
	}
}
