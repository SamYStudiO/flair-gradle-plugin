package flair.gradle.plugins

import flair.gradle.structure.IosStructure
import flair.gradle.variants.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IosPlugin extends AbstractPlatformPlugin
{
	public IosPlugin()
	{
		platform = Platform.IOS
	}

	@Override
	protected void addStructures()
	{
		addStructure( new IosStructure( ) )
	}

	@Override
	protected void addTasks()
	{
	}
}
