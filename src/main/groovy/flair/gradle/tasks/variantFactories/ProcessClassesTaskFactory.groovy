package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.process.ProcessClasses
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessClassesTaskFactory implements IVariantTaskFactory<ProcessClasses>
{
	public ProcessClasses create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_CLASSES.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		ProcessClasses t = project.tasks.findByName( name ) as ProcessClasses

		if( !t ) t = project.tasks.create( name , ProcessClasses )

		t.group = TaskDefinition.PROCESS_CLASSES.group.name
		t.variant = variant

		return t
	}
}
