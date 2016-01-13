package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantConfigurationExtension
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractPlatformPlugin
{
	public void addTasks()
	{
		super.addTasks( )

		addTask( Task.PROCESS_ANDROID_RESOURCES.name , Task.PROCESS_ANDROID_RESOURCES.type )
		addTask( Task.PROCESS_PROD_ANDROID_RESOURCES.name , Task.PROCESS_PROD_ANDROID_RESOURCES.type )
	}

	public void addExtensions()
	{
		addConfigurationExtension( "android" , VariantConfigurationExtension , flair )
	}
}
