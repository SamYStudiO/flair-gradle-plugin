package flair.gradle.tasks.variantFactories

import flair.gradle.plugins.PluginManager
import flair.gradle.plugins.TexturePackerPlugin
import flair.gradle.tasks.Tasks
import flair.gradle.tasks.process.ProcessResources
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessResourcesTaskFactory implements IVariantTaskFactory<ProcessResources>
{
	public ProcessResources create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.PROCESS_RESOURCES.name + variantName

		ProcessResources t = project.tasks.findByName( name ) as ProcessResources

		if( !t ) t = project.tasks.create( name , ProcessResources )

		t.group = Tasks.PROCESS_RESOURCES.group.name
		t.variant = variant

		if( PluginManager.hasPlugin( project , TexturePackerPlugin ) ) t.dependsOn Tasks.PUBLISH_ATLASES.name + variantName

		return t
	}
}
