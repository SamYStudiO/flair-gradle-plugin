package flair.gradle.plugins

import flair.gradle.structures.AtlasesStructure
import flair.gradle.structures.IStructure
import flair.gradle.tasks.TaskDefinition

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
		project.tasks.create( TaskDefinition.PUBLISH_ATLASES.name , TaskDefinition.PUBLISH_ATLASES.type )
	}
}
