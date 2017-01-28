package flair.gradle.tasks.variantFactories

import flair.gradle.plugins.PluginManager
import flair.gradle.plugins.TexturePackerPlugin
import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessResources
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessResourcesTaskFactory implements IVariantTaskFactory<ProcessResources>
{
	ProcessResources create( Project project , Variant variant )
	{
		String variantName = variant.getName( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.PROCESS_RESOURCES.name + variantName

		ProcessResources t = project.tasks.findByName( name ) as ProcessResources

		if( !t ) t = project.tasks.create( name , ProcessResources )

		t.group = TaskDefinition.PROCESS_RESOURCES.group.name
		t.variant = variant

		if( PluginManager.hasPlugin( project , TexturePackerPlugin ) ) t.dependsOn TaskDefinition.PUBLISH_ATLASES.name

		return t
	}
}
