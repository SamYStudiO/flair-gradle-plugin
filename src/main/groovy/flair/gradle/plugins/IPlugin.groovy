package flair.gradle.plugins

import flair.gradle.extensions.IConfigurationExtension
import flair.gradle.tasks.Group
import flair.gradle.variants.Platform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlugin extends Plugin<Project>
{
	public Task addTask( String name , Class type )

	public Task addTask( String name , Group group )

	public ExtensionAware addExtension( String name , Class type )

	public ExtensionAware addExtension( String name , Class type , ExtensionAware parent )

	public ExtensionAware addConfigurationExtension( String name , Platform platform , Class<IConfigurationExtension> type )

	public ExtensionAware addConfigurationExtension( String name , Platform platform , Class<IConfigurationExtension> type , ExtensionAware parent )
}
