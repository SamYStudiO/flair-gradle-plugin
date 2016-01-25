package flair.gradle.plugins

import flair.gradle.structures.AtlasesStructure
import flair.gradle.structures.IStructure
import flair.gradle.tasks.variantFactories.IVariantTaskFactory
import flair.gradle.tasks.variantFactories.PublishAtlasesTaskFactory

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractPlugin implements IStructurePlugin , IVariantTaskPlugin
{
	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new AtlasesStructure( ) )

		return list
	}

	@Override
	public List<IVariantTaskFactory> getVariantTaskFactories()
	{
		List<IVariantTaskFactory> list = new ArrayList<IVariantTaskFactory>( )

		list.add( new PublishAtlasesTaskFactory( ) )

		return list
	}
}
