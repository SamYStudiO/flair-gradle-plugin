package flair.gradle.plugins

import flair.gradle.structures.*

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
