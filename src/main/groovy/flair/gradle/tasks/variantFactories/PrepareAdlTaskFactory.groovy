package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.PrepareAdl
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PrepareAdlTaskFactory implements IVariantTaskFactory<PrepareAdl>
{

	public PrepareAdl create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.PREPARE_ADL.name + variantName

		PrepareAdl t = project.tasks.findByName( name ) as PrepareAdl

		if( !t ) t = project.tasks.create( name , PrepareAdl )

		t.group = Tasks.PREPARE_ADL.group.name
		t.variant = variant

		t.dependsOn Tasks.COMPILE.name + variantName

		return t
	}
}
