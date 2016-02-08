package flair.gradle.plugins

import flair.gradle.structures.AtlasesStructure
import flair.gradle.structures.IStructure
import flair.gradle.tasks.Tasks

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractPlugin implements IStructurePlugin
{
	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new AtlasesStructure( ) )

		return list
	}

	@Override
	protected void addTasks()
	{
		project.tasks.create( Tasks.PUBLISH_ATLASES.name , Tasks.PUBLISH_ATLASES.type )
	}
}
