package flair.gradle.plugins

import flair.gradle.structures.IStructure
import flair.gradle.structures.fdt.FdtClasspathStructure
import flair.gradle.structures.fdt.FdtCleanup
import flair.gradle.structures.fdt.FdtProjectStructure

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class FdtPlugin extends AbstractPlugin implements IdePlugin
{
	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )

		if( project.rootProject.file( ".project" ).exists( ) )
		{
			list.add( new FdtProjectStructure( ) )
			list.add( new FdtClasspathStructure( ) )
			list.add( new FdtCleanup( ) )
		}

		return list
	}
}
