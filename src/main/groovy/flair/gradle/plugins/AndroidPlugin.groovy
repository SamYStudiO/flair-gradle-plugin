package flair.gradle.plugins

import flair.gradle.structures.AndroidStructure
import flair.gradle.structures.IStructure
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
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new AndroidStructure( ) )

		return list
	}
}
