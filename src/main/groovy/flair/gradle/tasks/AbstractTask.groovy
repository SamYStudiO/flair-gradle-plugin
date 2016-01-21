package flair.gradle.tasks

import flair.gradle.extensions.IExtensionManager
import org.gradle.api.DefaultTask

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractTask extends DefaultTask implements ITask
{
	public IExtensionManager getExtensionManager()
	{
		return project.flair as IExtensionManager
	}
}
