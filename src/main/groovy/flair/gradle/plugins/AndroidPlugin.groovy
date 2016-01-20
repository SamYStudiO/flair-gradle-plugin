package flair.gradle.plugins

import flair.gradle.structure.AndroidStructure
import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractPlatformPlugin
{
	public AndroidPlugin()
	{
		platform = Platforms.ANDROID
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
