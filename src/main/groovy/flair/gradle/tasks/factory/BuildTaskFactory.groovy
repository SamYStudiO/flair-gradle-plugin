package flair.gradle.tasks.factory

import flair.gradle.tasks.Group
import flair.gradle.tasks.TaskManager
import flair.gradle.tasks.build.Build
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class BuildTaskFactory implements VariantTaskFactory<Build>
{
	public Build create( Project project , Variant variant )
	{
		String name = "build" + variant.name

		Build t = project.tasks.findByName( name ) as Build

		if( !t ) t = project.tasks.create( name , Build )

		t.group = Group.BUILD.name
		t.variant = variant
		t.dependsOn = [ TaskManager.getTask( project , Group.PROCESS , variant ).name ]

		return t
	}
}
