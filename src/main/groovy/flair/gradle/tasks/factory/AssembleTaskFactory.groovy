package flair.gradle.tasks.factory

import flair.gradle.tasks.Assemble
import flair.gradle.tasks.Group
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AssembleTaskFactory implements VariantTaskFactory<Assemble>
{
	public Assemble create( Project project , Variant variant )
	{
		String name = "assemble" + variant.name

		Assemble t = project.tasks.findByName( name ) as Assemble

		if( !t ) t = project.tasks.create( name , Assemble )

		t.group = Group.BUILD.name
		t.variant = variant

		return t
	}
}
