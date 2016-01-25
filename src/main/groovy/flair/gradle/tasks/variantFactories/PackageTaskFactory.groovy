package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Package
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PackageTaskFactory implements IVariantTaskFactory<Package>
{
	public Package create( Project project , Variant variant )
	{
		String name = Tasks.PACKAGE.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		Package t = project.tasks.findByName( name ) as Package

		if( !t ) t = project.tasks.create( name , Package )

		t.group = Tasks.PACKAGE.group.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Tasks.COMPILE.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE ) ).name

		return t
	}
}
