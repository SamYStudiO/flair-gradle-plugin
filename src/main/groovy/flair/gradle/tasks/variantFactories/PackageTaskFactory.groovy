package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Group
import flair.gradle.tasks.Packaging
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PackageTaskFactory implements IVariantTaskFactory<Packaging>
{
	public Packaging create( Project project , Variant variant )
	{
		String name = "package" + variant.name

		Packaging t = project.tasks.findByName( name ) as Packaging

		if( !t ) t = project.tasks.create( name , Packaging )

		t.group = Group.PACKAGE.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Group.COMPILE.name + variant.name ).name

		return t
	}
}
