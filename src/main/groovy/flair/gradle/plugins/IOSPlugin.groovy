package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantConfigurationExtension
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
		addConfigurationExtension( "ios" , VariantConfigurationExtension , flair )
	}
}
