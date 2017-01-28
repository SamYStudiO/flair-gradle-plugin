package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessAppDescriptor
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAppDescriptorTaskFactory implements IVariantTaskFactory<ProcessAppDescriptor>
{
	ProcessAppDescriptor create( Project project , Variant variant )
	{
		String variantName = variant.getName( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.PROCESS_APP_DESCRIPTOR.name + variantName

		ProcessAppDescriptor t = project.tasks.findByName( name ) as ProcessAppDescriptor

		if( !t ) t = project.tasks.create( name , ProcessAppDescriptor )

		t.group = TaskDefinition.PROCESS_APP_DESCRIPTOR.group.name
		t.variant = variant
		t.dependsOn TaskDefinition.PROCESS_EXTENSIONS.name + variantName , TaskDefinition.PROCESS_ICONS.name + variantName

		return t
	}
}
