package flair.gradle.plugins

import flair.gradle.structure.IStructure
import flair.gradle.structure.IosStructure
import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IosPlugin extends AbstractPlatformPlugin
{
	public IosPlugin()
	{
		platform = Platforms.IOS
	}

	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new IosStructure( ) )

		return list
	}
}
