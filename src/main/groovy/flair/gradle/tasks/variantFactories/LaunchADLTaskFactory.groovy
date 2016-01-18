package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Group
import flair.gradle.tasks.LaunchADL
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchADLTaskFactory implements IVariantTaskFactory<LaunchADL>
{
	public LaunchADL create( Project project , Variant variant )
	{
		String name = "launchADL" + variant.name

		LaunchADL t = project.tasks.findByName( name ) as LaunchADL

		if( !t ) t = project.tasks.create( name , LaunchADL )

		t.group = Group.LAUNCH.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Group.COMPILE.name + variant.name ).name

		return t
	}
}
