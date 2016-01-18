package flair.gradle.extensions.configuration

import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractExtension
{
	protected String name

	protected Project project

	protected Platform platform

	public AbstractExtension( String name , Project project , Platform platform )
	{
		this.name = name
		this.project = project
		this.platform = platform
	}

	public String getName()
	{
		return name
	}

	public Project getProject()
	{
		return project
	}

	public Platform getPlatform()
	{
		return platform
	}
}
