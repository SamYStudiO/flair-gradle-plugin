package flair.gradle.plugins

import flair.gradle.extensions.factories.IExtensionFactory
import flair.gradle.extensions.factories.TexturePackerExtensionFactory
import flair.gradle.structure.AtlasesStructure
import flair.gradle.structure.IStructure
import flair.gradle.tasks.variantFactories.IVariantTaskFactory
import flair.gradle.tasks.variantFactories.PublishAtlasesTaskFactory
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractPlugin implements IExtensionPlugin , IStructurePlugin , IVariantTaskPlugin
{
	@Override
	public void apply( Project project )
	{
		project.apply( plugin: BasePlugin )

		super.apply( project )
	}

	@Override
	public IExtensionFactory getExtensionFactory()
	{
		return new TexturePackerExtensionFactory( )
	}

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
