package flair.gradle.tasks

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.DefaultTask

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractTask extends DefaultTask implements ITask
{
	protected IExtensionManager getExtensionManager()
	{
		return project.flair as IExtensionManager
	}

	protected File getModuleDir()
	{
		return project.file( extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name ) )
	}
}
