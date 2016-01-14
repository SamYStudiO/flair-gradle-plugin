package flair.gradle.plugins

import flair.gradle.extensions.configuration.ConfigurationExtension
import flair.gradle.platforms.Platform
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSPlugin extends AbstractPlatformPlugin
{
	public void addTasks()
	{
		super.addTasks( )

		addTask( Task.PROCESS_IOS_RESOURCES.name , Task.PROCESS_IOS_RESOURCES.type )
		addTask( Task.PROCESS_PROD_IOS_RESOURCES.name , Task.PROCESS_PROD_IOS_RESOURCES.type )
	}

	public void addExtensions()
	{
		addConfigurationExtension( Platform.IOS.name.toLowerCase( ) , Platform.IOS , ConfigurationExtension , flair )
	}
}
