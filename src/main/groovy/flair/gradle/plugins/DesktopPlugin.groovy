package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantConfigurationExtension
import flair.gradle.tasks.Tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlugin
{
	public void addTasks()
	{
		addTask( Tasks.SCAFFOLD.name , Tasks.SCAFFOLD.type )
		addTask( Tasks.UPDATE_PROPERTIES.name , Tasks.UPDATE_PROPERTIES.type )
		addTask( Tasks.PROCESS_DESKTOP_RESOURCES.name , Tasks.PROCESS_DESKTOP_RESOURCES.type )
		addTask( Tasks.PROCESS_PROD_DESKTOP_RESOURCES.name , Tasks.PROCESS_PROD_DESKTOP_RESOURCES.type )
		addTask( Tasks.INCREMENT_VERSION.name , Tasks.INCREMENT_VERSION.type )
		addTask( Tasks.GENERATE_FONT_CLASS.name , Tasks.GENERATE_FONT_CLASS.type )
		addTask( Tasks.AUTO_GENERATE_FONT_CLASS.name , Tasks.AUTO_GENERATE_FONT_CLASS.type )
		addTask( Tasks.GENERATE_RESOURCE_CLASS.name , Tasks.GENERATE_RESOURCE_CLASS.type )
		addTask( Tasks.AUTO_GENERATE_RESOURCE_CLASS.name , Tasks.AUTO_GENERATE_RESOURCE_CLASS.type )
	}

	public void addExtensions()
	{
		addConfigurationExtension( "desktop" , VariantConfigurationExtension , flair )
	}
}
