package flair.gradle.extensions

import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractExtension
{
	protected String name

	protected Project project

	protected Platforms platform

	public AbstractExtension( String name , Project project , Platforms platform )
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

	public Platforms getPlatform()
	{
		return platform
	}

	public IPlatformExtensionManager getExtensionManager()
	{
		return project.flair as IPlatformExtensionManager
	}
}
