package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantContainer
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AssembleTaskFactory implements IVariantTaskFactory<VariantContainer>
{
	public VariantContainer create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = TaskDefinition.ASSEMBLE.name + variantName

		VariantContainer t = project.tasks.findByName( name ) as VariantContainer

		if( !t ) t = project.tasks.create( name , VariantContainer )

		t.group = TaskDefinition.ASSEMBLE.group.name
		t.variant = variant
		t.dependsOn TaskDefinition.values( ).findAll { it.group == TaskGroup.PROCESS }.collect { it.name + variantName }

		return t
	}
}
