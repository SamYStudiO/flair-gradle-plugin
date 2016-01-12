package flair.gradle.extensions.configuration

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractExtension
{
	protected String name

	protected Project project

	public AbstractExtension( String name , Project project )
	{
		this.name = name
		this.project = project
	}

	public String getName()
	{
		return name
	}

	public Project getProject()
	{
		return project
	}
}
