package flair.gradle.plugins

import flair.gradle.dependencies.Config

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IConfigurationPlugin
{
	List<Config> getConfigurations()
}