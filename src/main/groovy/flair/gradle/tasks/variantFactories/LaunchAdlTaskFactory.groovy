package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.LaunchAdl
import flair.gradle.tasks.TaskDefinition
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchAdlTaskFactory implements IVariantTaskFactory<LaunchAdl>
{
	public LaunchAdl create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = TaskDefinition.LAUNCH_ADL.name + variantName

		LaunchAdl t = project.tasks.findByName( name ) as LaunchAdl

		if( !t ) t = project.tasks.create( name , LaunchAdl )

		t.group = TaskDefinition.LAUNCH_ADL.group.name
		t.variant = variant
		t.dependsOn TaskDefinition.PREPARE_PACKAGE.name + variantName

		return t
	}
}
