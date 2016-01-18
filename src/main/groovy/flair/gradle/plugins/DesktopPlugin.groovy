package flair.gradle.plugins

import flair.gradle.structure.DesktopStructure
import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopPlugin extends AbstractPlatformPlugin
{
	public DesktopPlugin()
	{
		platform = Platform.DESKTOP
	}

	@Override
	public void apply( Project project )
	{
		project.apply( plugin: "flair.base" )

		super.apply( project )
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
