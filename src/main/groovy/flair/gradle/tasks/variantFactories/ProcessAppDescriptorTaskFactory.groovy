package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Tasks
import flair.gradle.tasks.process.ProcessAppDescriptor
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAppDescriptorTaskFactory implements IVariantTaskFactory<ProcessAppDescriptor>
{
	public ProcessAppDescriptor create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.PROCESS_APP_DESCRIPTOR.name + variantName

		ProcessAppDescriptor t = project.tasks.findByName( name ) as ProcessAppDescriptor

		if( !t ) t = project.tasks.create( name , ProcessAppDescriptor )

		t.group = Tasks.PROCESS_APP_DESCRIPTOR.group.name
		t.variant = variant
		t.dependsOn Tasks.PROCESS_EXTENSIONS.name + variantName

		return t
	}
}
