package flair.gradle.plugins

import flair.gradle.structure.DesktopStructure
import flair.gradle.structure.IStructure
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
