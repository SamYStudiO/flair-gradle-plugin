package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Tasks
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
		String name = Tasks.UNINSTALL.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		Uninstall t = project.tasks.findByName( name ) as Uninstall

		if( !t ) t = project.tasks.create( name , Uninstall )

		t.group = Tasks.UNINSTALL.group.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Tasks.PACKAGE.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE ) ).name

		return t
	}
}
