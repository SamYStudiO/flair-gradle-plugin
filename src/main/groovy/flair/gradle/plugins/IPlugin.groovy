package flair.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlugin extends Plugin<Project>
{
	public Project getProject()
}
