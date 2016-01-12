package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantConfigurationExtension
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlugin
{
	public void addTasks()
	{
		addTask( Task.SCAFFOLD.name , Task.SCAFFOLD.type )
		addTask( Task.UPDATE_PROPERTIES.name , Task.UPDATE_PROPERTIES.type )
		addTask( Task.PROCESS_DESKTOP_RESOURCES.name , Task.PROCESS_DESKTOP_RESOURCES.type )
		addTask( Task.PROCESS_PROD_DESKTOP_RESOURCES.name , Task.PROCESS_PROD_DESKTOP_RESOURCES.type )
		addTask( Task.INCREMENT_VERSION.name , Task.INCREMENT_VERSION.type )
		addTask( Task.GENERATE_FONT_CLASS.name , Task.GENERATE_FONT_CLASS.type )
		addTask( Task.AUTO_GENERATE_FONT_CLASS.name , Task.AUTO_GENERATE_FONT_CLASS.type )
		addTask( Task.GENERATE_RESOURCE_CLASS.name , Task.GENERATE_RESOURCE_CLASS.type )
		addTask( Task.AUTO_GENERATE_RESOURCE_CLASS.name , Task.AUTO_GENERATE_RESOURCE_CLASS.type )
	}

	public void addExtensions()
	{
		addConfigurationExtension( "desktop" , VariantConfigurationExtension , flair )
	}
}
