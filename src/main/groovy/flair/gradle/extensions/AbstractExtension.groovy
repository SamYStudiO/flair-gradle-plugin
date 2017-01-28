package flair.gradle.extensions

import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
abstract class AbstractExtension implements IExtension
{
	private String name

	private Project project

	AbstractExtension( String name , Project project )
	{
		this.name = name
		this.project = project
	}

	@Override
	String getName()
	{
		return name
	}

	@Override
	Project getProject()
	{
		return project
	}

	@Override
	IExtensionManager getExtensionManager()
	{
		return project.flair as IExtensionManager
	}

	@Override
	Object getProp( String property , Variant variant )
	{
		return getProp( property , variant , false )
	}

	@Override
	abstract Object getProp( String property , Variant variant , boolean returnDefaultIfNull )
}
