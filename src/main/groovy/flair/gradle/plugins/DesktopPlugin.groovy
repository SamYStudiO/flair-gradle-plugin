package flair.gradle.plugins

import flair.gradle.structure.DesktopStructure
import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlatformPlugin
{
	public DesktopPlugin()
	{
		platform = Platforms.DESKTOP
	}

	@Override
	protected void addStructures()
	{
		addStructure( new DesktopStructure( ) )
	}

	@Override
	protected void addTasks()
	{
	}
}
