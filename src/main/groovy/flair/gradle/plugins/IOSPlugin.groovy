package flair.gradle.plugins

import flair.gradle.structure.IOSStructure
import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IOSPlugin extends AbstractPlatformPlugin
{
	public IOSPlugin()
	{
		platform = Platform.IOS
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
		addStructure( new IOSStructure( ) )
	}

	@Override
	protected void addTasks()
	{
	}
}
