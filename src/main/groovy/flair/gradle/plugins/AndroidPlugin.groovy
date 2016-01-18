package flair.gradle.plugins

import flair.gradle.structure.AndroidStructure
import flair.gradle.variants.Platform

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
		super.addStructures( )
		addStructure( new AndroidStructure( ) )
	}
}
