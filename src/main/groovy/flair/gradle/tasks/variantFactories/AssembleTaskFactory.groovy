package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AssembleTaskFactory implements IVariantTaskFactory<VariantTask>
{
	public VariantTask create( Project project , Variant variant )
	{
		String variantName = variant.getName( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.ASSEMBLE.name + variantName

		VariantTask t = project.tasks.findByName( name ) as VariantTask

		if( !t ) t = project.tasks.create( name , VariantTask )

		t.group = TaskDefinition.ASSEMBLE.group.name
		t.description = "Handler to run all processes from ${ variant.name } variant"
		t.variant = variant
		t.dependsOn TaskDefinition.values( ).findAll { it.group == TaskGroup.PROCESS }.collect { it.name + variantName }

		return t
	}
}
