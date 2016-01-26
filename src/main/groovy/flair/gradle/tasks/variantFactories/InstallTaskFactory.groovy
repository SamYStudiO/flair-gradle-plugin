package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Install
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class InstallTaskFactory implements IVariantTaskFactory<Install>
{
	public Install create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.INSTALL.name + variantName

		Install t = project.tasks.findByName( name ) as Install

		if( !t ) t = project.tasks.create( name , Install )

		t.group = Tasks.INSTALL.group.name
		t.variant = variant
		t.dependsOn Tasks.PACKAGE.name + variantName

		return t
	}
}
