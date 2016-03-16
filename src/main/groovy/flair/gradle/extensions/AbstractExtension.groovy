package flair.gradle.extensions

import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractExtension implements IExtension
{
	private String name

	private Project project

	public AbstractExtension( String name , Project project )
	{
		this.name = name
		this.project = project
	}

	@Override
	public String getName()
	{
		return name
	}

	@Override
	public Project getProject()
	{
		return project
	}

	@Override
	public IExtensionManager getExtensionManager()
	{
		return project.flair as IExtensionManager
	}

	@Override
	public Object getProp( String property , Variant variant )
	{
		return getProp( property , variant , false )
	}

	@Override
	public abstract Object getProp( String property , Variant variant , boolean returnDefaultIfNull )
}
