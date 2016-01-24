package flair.gradle.plugins

import flair.gradle.structures.DesktopStructure
import flair.gradle.structures.IStructure
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
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new DesktopStructure( ) )

		return list
	}
}
