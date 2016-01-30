package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.LaunchAdl
import flair.gradle.tasks.Tasks
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
		String name = Tasks.LAUNCH_ADL.name + variantName

		LaunchAdl t = project.tasks.findByName( name ) as LaunchAdl

		if( !t ) t = project.tasks.create( name , LaunchAdl )

		t.group = Tasks.LAUNCH_ADL.group.name
		t.variant = variant
		t.dependsOn Tasks.COMPILE.name + variantName , Tasks.PROCESS_APP_DESCRIPTOR.name + variantName ,
				Tasks.PROCESS_RESOURCES.name + variantName , Tasks.PROCESS_ASSETS.name + variantName , Tasks.PROCESS_ICONS.name + variantName ,
				Tasks.PROCESS_SPLASHS.name + variantName

		return t
	}
}
