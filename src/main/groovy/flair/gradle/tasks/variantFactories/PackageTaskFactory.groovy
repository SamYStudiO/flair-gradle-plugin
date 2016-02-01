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
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.PACKAGE.name + variantName

		Package t = project.tasks.findByName( name ) as Package

		if( !t ) t = project.tasks.create( name , Package )

		t.group = Tasks.PACKAGE.group.name
		t.variant = variant
		t.dependsOn Tasks.COMPILE.name + variantName , Tasks.PROCESS_APP_DESCRIPTOR.name + variantName

		return t
	}
}
