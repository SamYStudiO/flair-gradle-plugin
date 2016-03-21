package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessExtensions
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessExtensionsTaskFactory implements IVariantTaskFactory<ProcessExtensions>
{
	public ProcessExtensions create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_EXTENSIONS.name + variant.getName( Variant.NamingType.CAPITALIZE )

		ProcessExtensions t = project.tasks.findByName( name ) as ProcessExtensions

		if( !t ) t = project.tasks.create( name , ProcessExtensions )

		t.group = TaskDefinition.PROCESS_EXTENSIONS.group.name
		t.variant = variant

		return t
	}
}
