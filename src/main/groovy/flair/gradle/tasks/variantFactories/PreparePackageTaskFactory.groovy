package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.PreparePackage
import flair.gradle.tasks.TaskDefinition
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PreparePackageTaskFactory implements IVariantTaskFactory<PreparePackage>
{

	PreparePackage create( Project project , Variant variant )
	{
		String variantName = variant.getName( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.PREPARE_PACKAGE.name + variantName

		PreparePackage t = project.tasks.findByName( name ) as PreparePackage

		if( !t ) t = project.tasks.create( name , PreparePackage )

		t.group = TaskDefinition.PREPARE_PACKAGE.group.name
		t.variant = variant

		t.dependsOn TaskDefinition.COMPILE.name + variantName , TaskDefinition.PROCESS_APP_DESCRIPTOR.name + variantName ,
				TaskDefinition.PROCESS_RESOURCES.name + variantName , TaskDefinition.PROCESS_ASSETS.name + variantName ,
				TaskDefinition.PROCESS_SPLASH_SCREENS.name + variantName , TaskDefinition.PROCESS_SIGNING.name + variantName

		return t
	}
}
