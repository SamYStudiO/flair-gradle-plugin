package flair.gradle.plugins

import flair.gradle.structure.AndroidStructure
import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractPlatformPlugin
{
	public AndroidPlugin()
	{
		platform = Platform.ANDROID
	}

	@Override
	protected void addStructures()
	{
		addStructure( new AndroidStructure( ) )
	}

	@Override
	protected void addTasks()
	{
	}
}
