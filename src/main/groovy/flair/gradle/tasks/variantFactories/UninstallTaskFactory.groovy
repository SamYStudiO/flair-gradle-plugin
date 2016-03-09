package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.Uninstall
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class UninstallTaskFactory implements IVariantTaskFactory<Uninstall>
{
	public Uninstall create( Project project , Variant variant )
	{
		String name = TaskDefinition.UNINSTALL.name + variant.getNameWithType( Variant.NamingType.CAPITALIZE )

		Uninstall t = project.tasks.findByName( name ) as Uninstall

		if( !t ) t = project.tasks.create( name , Uninstall )

		t.group = TaskDefinition.UNINSTALL.group.name
		t.variant = variant

		return t
	}
}
