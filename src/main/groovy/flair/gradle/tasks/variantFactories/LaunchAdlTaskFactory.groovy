package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Groups
import flair.gradle.tasks.LaunchAdl
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchAdlTaskFactory implements IVariantTaskFactory<LaunchAdl>
{
	public LaunchAdl create( Project project , Variant variant )
	{
		String name = "launchAdl" + variant.name

		LaunchAdl t = project.tasks.findByName( name ) as LaunchAdl

		if( !t ) t = project.tasks.create( name , LaunchAdl )

		t.group = Groups.LAUNCH.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Groups.COMPILE.name + variant.name ).name

		return t
	}
}
