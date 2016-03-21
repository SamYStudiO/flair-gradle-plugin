package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessSigning
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSigningTaskFactory implements IVariantTaskFactory<ProcessSigning>
{
	public ProcessSigning create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_SIGNING.name + variant.getName( Variant.NamingType.CAPITALIZE )

		ProcessSigning t = project.tasks.findByName( name ) as ProcessSigning

		if( !t ) t = project.tasks.create( name , ProcessSigning )

		t.group = TaskDefinition.PROCESS_SIGNING.group.name
		t.variant = variant

		return t
	}
}
