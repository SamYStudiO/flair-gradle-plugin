package flair.gradle.plugins

import flair.gradle.structures.IStructure
import flair.gradle.structures.fdt.*

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class FdtPlugin extends AbstractPlugin implements IdePlugin
{
	@Override
	List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )

		if( project.rootProject.file( ".project" ).exists( ) )
		{
			list.add( new FdtProjectStructure( ) )
			list.add( new FdtClasspathStructure( ) )
			list.add( new FdtCleanup( ) )
			list.add( new FdtCoreStructure( ) )
			list.add( new FdtRunDebugConfigurationsStructure( ) )
		}

		return list
	}
}
