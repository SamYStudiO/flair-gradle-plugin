package flair.gradle.plugins

import flair.gradle.extensions.configuration.ConfigurationExtension
import flair.gradle.platforms.Platform
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlatformPlugin
{
	public void addTasks()
	{
		super.addTasks( )

		addTask( Task.PROCESS_DESKTOP_RESOURCES.name , Task.PROCESS_DESKTOP_RESOURCES.type )
		addTask( Task.PROCESS_PROD_DESKTOP_RESOURCES.name , Task.PROCESS_PROD_DESKTOP_RESOURCES.type )
	}

	public void addExtensions()
	{
		addConfigurationExtension( Platform.DESKTOP.name.toLowerCase( ) , Platform.DESKTOP , ConfigurationExtension , flair )
	}
}
