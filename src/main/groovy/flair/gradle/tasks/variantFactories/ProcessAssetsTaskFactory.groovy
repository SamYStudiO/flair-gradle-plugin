package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessAssets
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAssetsTaskFactory implements IVariantTaskFactory<ProcessAssets>
{
	public ProcessAssets create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_ASSETS.name + variant.getName( Variant.NamingType.CAPITALIZE )

		ProcessAssets t = project.tasks.findByName( name ) as ProcessAssets

		if( !t ) t = project.tasks.create( name , ProcessAssets )

		t.group = TaskDefinition.PROCESS_ASSETS.group.name
		t.variant = variant

		return t
	}
}
