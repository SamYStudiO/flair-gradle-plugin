package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Install
import flair.gradle.tasks.TaskDefinition
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class InstallTaskFactory implements IVariantTaskFactory<Install>
{
	Install create( Project project , Variant variant )
	{
		String variantName = variant.getName( Variant.NamingType.CAPITALIZE )
		String name = TaskDefinition.INSTALL.name + variantName

		Install t = project.tasks.findByName( name ) as Install

		if( !t ) t = project.tasks.create( name , Install )

		t.group = TaskDefinition.INSTALL.group.name
		t.variant = variant
		t.dependsOn TaskDefinition.PACKAGE.name + variantName

		return t
	}
}
