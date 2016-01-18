package flair.gradle.plugins

import flair.gradle.structure.DesktopStructure
import flair.gradle.variants.Platform

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
	protected void addStructures()
	{
		super.addStructures( )
		addStructure( new DesktopStructure( ) )
	}
}
