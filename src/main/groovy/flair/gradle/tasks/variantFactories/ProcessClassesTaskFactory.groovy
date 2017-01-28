package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessClasses
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessClassesTaskFactory implements IVariantTaskFactory<ProcessClasses>
{
	ProcessClasses create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_CLASSES.name + variant.getName( Variant.NamingType.CAPITALIZE )

		ProcessClasses t = project.tasks.findByName( name ) as ProcessClasses

		if( !t ) t = project.tasks.create( name , ProcessClasses )

		t.group = TaskDefinition.PROCESS_CLASSES.group.name
		t.variant = variant

		return t
	}
}
