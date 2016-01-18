package flair.gradle.tasks.factory

import flair.gradle.tasks.Compile
import flair.gradle.tasks.Group
import flair.gradle.tasks.TaskManager
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class CompileTaskFactory implements VariantTaskFactory<Compile>
{
	public Compile create( Project project , Variant variant )
	{
		String name = "compile" + variant.name

		Compile t = project.tasks.findByName( name ) as Compile

		if( !t ) t = project.tasks.create( name , Compile )

		t.group = Group.BUILD.name
		t.variant = variant
		t.dependsOn TaskManager.getVariantTask( project , Group.ASSEMBLE , variant )

		return t
	}
}
