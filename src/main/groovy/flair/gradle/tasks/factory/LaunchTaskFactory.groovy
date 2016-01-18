package flair.gradle.tasks.factory

import flair.gradle.tasks.Group
import flair.gradle.tasks.Launch
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchTaskFactory implements VariantTaskFactory<Launch>
{
	public Launch create( Project project , Variant variant )
	{
		String name = "launch" + variant.name

		Launch t = project.tasks.findByName( name ) as Launch

		if( !t ) t = project.tasks.create( name , Launch )

		t.group = Group.LAUNCH.name
		t.variant = variant

		return t
	}
}
