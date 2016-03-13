package flair.gradle.plugins

import flair.gradle.structures.IStructure
import flair.gradle.structures.idea.IdeaImlStructure
import flair.gradle.structures.idea.IdeaLibrariesStructure
import flair.gradle.structures.idea.IdeaModulesStructure
import flair.gradle.structures.idea.IdeaRunDebugConfigurationsStructure

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaPlugin extends AbstractPlugin implements IdePlugin
{
	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )

		if( project.rootProject.file( ".idea" ).exists( ) )
		{
			list.add( new IdeaImlStructure( ) )
			list.add( new IdeaLibrariesStructure( ) )
			list.add( new IdeaModulesStructure( ) )
			list.add( new IdeaRunDebugConfigurationsStructure( ) )
		}

		return list
	}
}
