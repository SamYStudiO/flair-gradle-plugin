package flair.gradle.tasks.factory

import flair.gradle.tasks.Group
import flair.gradle.tasks.Packaging
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PackageTaskFactory implements VariantTaskFactory<Packaging>
{
	public Packaging create( Project project , Variant variant )
	{
		String name = "package" + variant

		Packaging t = project.tasks.findByName( name ) as Packaging

		if( !t ) t = project.tasks.create( name , Packaging )

		t.group = Group.PACKAGE.name
		t.variant = variant

		return t
	}
}
