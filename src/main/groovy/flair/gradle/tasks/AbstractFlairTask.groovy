package flair.gradle.tasks

import flair.gradle.extensions.IPlatformExtensionManager
import org.gradle.api.DefaultTask

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractFlairTask extends DefaultTask implements IFlairTask
{
	public IPlatformExtensionManager getExtensionManager()
	{
		return project.flair as IPlatformExtensionManager
	}
}
