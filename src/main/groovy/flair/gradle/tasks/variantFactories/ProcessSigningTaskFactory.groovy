package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Tasks
import flair.gradle.tasks.process.ProcessSigning
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSigningTaskFactory implements IVariantTaskFactory<ProcessSigning>
{
	public ProcessSigning create( Project project , Variant variant )
	{
		String name = Tasks.PROCESS_SIGNING.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		ProcessSigning t = project.tasks.findByName( name ) as ProcessSigning

		if( !t ) t = project.tasks.create( name , ProcessSigning )

		t.group = Tasks.PROCESS_SIGNING.group.name
		t.variant = variant

		return t
	}
}
