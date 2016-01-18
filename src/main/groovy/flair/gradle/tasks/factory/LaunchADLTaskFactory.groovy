package flair.gradle.tasks.factory

import flair.gradle.tasks.Group
import flair.gradle.tasks.LaunchADL
import flair.gradle.tasks.TaskManager
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchADLTaskFactory implements VariantTaskFactory<LaunchADL>
{
	public LaunchADL create( Project project , Variant variant )
	{
		String name = "launchDevice" + variant.name

		LaunchADL t = project.tasks.findByName( name ) as LaunchADL

		if( !t ) t = project.tasks.create( name , LaunchADL )

		t.group = Group.LAUNCH.name
		t.variant = variant
		t.dependsOn TaskManager.getVariantTask( project , Group.COMPILE , variant )

		return t
	}
}
