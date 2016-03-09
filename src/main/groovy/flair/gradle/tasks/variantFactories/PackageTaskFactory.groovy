package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Package
import flair.gradle.tasks.TaskDefinition
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PackageTaskFactory implements IVariantTaskFactory<Package>
{
	public Package create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.PACKAGE.name + variantName

		Package t = project.tasks.findByName( name ) as Package

		if( !t ) t = project.tasks.create( name , Package )

		t.group = TaskDefinition.PACKAGE.group.name
		t.variant = variant
		t.dependsOn TaskDefinition.PREPARE_PACKAGE.name + variantName

		return t
	}
}
