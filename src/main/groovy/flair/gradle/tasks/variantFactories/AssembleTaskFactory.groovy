package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Assemble
import flair.gradle.tasks.Groups
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AssembleTaskFactory implements IVariantTaskFactory<Assemble>
{
	public Assemble create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.ASSEMBLE.name + variantName

		Assemble t = project.tasks.findByName( name ) as Assemble

		if( !t ) t = project.tasks.create( name , Assemble )

		t.group = Tasks.ASSEMBLE.group.name
		t.variant = variant
		t.dependsOn Tasks.values( ).findAll { it.group == Groups.PROCESS }.collect { it.name + variantName }

		return t
	}
}
