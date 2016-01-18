package flair.gradle.plugins

import flair.gradle.structure.IOSStructure
import flair.gradle.variants.Platform

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
	protected void addStructures()
	{
		super.addStructures( )
		addStructure( new IOSStructure( ) )
	}
}
