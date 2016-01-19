package flair.gradle.plugins

import flair.gradle.extensions.TexturePackerExtension
import flair.gradle.structure.AtlasesStructure
import flair.gradle.tasks.variantFactories.IVariantTaskFactory
import flair.gradle.tasks.variantFactories.PublishAtlasesTaskFactory
import flair.gradle.variants.Platform
import flair.gradle.variants.Variant
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class TexturePackerPlugin extends AbstractStructurePlugin implements IVariantTaskPlugin
{
	protected List<IVariantTaskFactory> variantFactories = new ArrayList<IVariantTaskFactory>( )

	@Override
	public void apply( Project project )
	{
		project.apply( plugin: BasePlugin )

		super.apply( project )

		addVariantTaskFactory( new PublishAtlasesTaskFactory( ) )

		project.afterEvaluate {
			updateVariantTasks( )
		}
	}

	@Override
	public final void addVariantTaskFactory( IVariantTaskFactory factory )
	{
		variantFactories.add( factory )
	}

	@Override
	public final void updateVariantTasks()
	{
		variantFactories.each {

			List<Variant> list

			if( PluginManager.hasPlatformPlugin( project , Platform.IOS ) )
			{
				list = flairExtension.getAllVariants( Platform.IOS )
				if( list.size( ) ) list.each { variant -> it.create( project , variant ) } else it.create( project , new Variant( project , Platform.IOS ) )
			}

			if( PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) )
			{
				list = flairExtension.getAllVariants( Platform.ANDROID )
				if( list.size( ) ) list.each { variant -> it.create( project , variant ) } else it.create( project , new Variant( project , Platform.ANDROID ) )
			}

			if( PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) )
			{
				list = flairExtension.getAllVariants( Platform.DESKTOP )
				if( list.size( ) ) list.each { variant -> it.create( project , variant ) } else it.create( project , new Variant( project , Platform.DESKTOP ) )
			}
		}
	}

	@Override
	public void addTasks()
	{
	}

	@Override
	public void addExtensions()
	{
		addExtension( TexturePackerExtension.NAME , TexturePackerExtension , flairExtension as ExtensionAware )
	}

	@Override
	protected void addStructures()
	{
		addStructure( new AtlasesStructure( ) )
	}
}
