package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.Uninstall
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class UninstallTaskFactory implements IVariantTaskFactory<Uninstall>
{
	Uninstall create( Project project , Variant variant )
	{
		String name = TaskDefinition.UNINSTALL.name + variant.getName( Variant.NamingType.CAPITALIZE )

		Uninstall t = project.tasks.findByName( name ) as Uninstall

		if( !t ) t = project.tasks.create( name , Uninstall )

		t.group = TaskDefinition.UNINSTALL.group.name
		t.variant = variant

		return t
	}
}
