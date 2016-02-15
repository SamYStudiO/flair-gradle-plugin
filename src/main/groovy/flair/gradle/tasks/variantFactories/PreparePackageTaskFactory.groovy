package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.PreparePackage
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PreparePackageTaskFactory implements IVariantTaskFactory<PreparePackage>
{

	public PreparePackage create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.PREPARE_PACKAGE.name + variantName

		PreparePackage t = project.tasks.findByName( name ) as PreparePackage

		if( !t ) t = project.tasks.create( name , PreparePackage )

		t.group = Tasks.PREPARE_PACKAGE.group.name
		t.variant = variant

		t.dependsOn Tasks.COMPILE.name + variantName , Tasks.PROCESS_APP_DESCRIPTOR.name + variantName ,
				Tasks.PROCESS_RESOURCES.name + variantName , Tasks.PROCESS_ASSETS.name + variantName , Tasks.PROCESS_ICONS.name + variantName ,
				Tasks.PROCESS_SPLASHS.name + variantName , Tasks.PROCESS_SIGNING.name + variantName

		return t
	}
}
