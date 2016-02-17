package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.process.ProcessLibraries
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessLibrariesTaskFactory implements IVariantTaskFactory<ProcessLibraries>
{
	public ProcessLibraries create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_LIBRARIES.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		ProcessLibraries t = project.tasks.findByName( name ) as ProcessLibraries

		if( !t ) t = project.tasks.create( name , ProcessLibraries )

		t.group = TaskDefinition.PROCESS_LIBRARIES.group.name
		t.variant = variant

		return t
	}
}
