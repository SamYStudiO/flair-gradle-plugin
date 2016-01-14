package flair.gradle.plugins

import flair.gradle.platforms.Platform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlugin extends Plugin<Project>
{
	public void addTasks()

	public void addExtensions()

	public Task addTask( String name , Class type )

	public ExtensionAware addExtension( String name , Class type )

	public ExtensionAware addExtension( String name , Class type , ExtensionAware parent )

	public ExtensionAware addConfigurationExtension( String name , Platform platform , Class type )

	public ExtensionAware addConfigurationExtension( String name , Platform platform , Class type , ExtensionAware parent )
}
