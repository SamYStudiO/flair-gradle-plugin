package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Group
import flair.gradle.tasks.Install
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class InstallTaskFactory implements IVariantTaskFactory<Install>
{
	public Install create( Project project , Variant variant )
	{
		String name = "install" + variant.name

		Install t = project.tasks.findByName( name ) as Install

		if( !t ) t = project.tasks.create( name , Install )

		t.group = Group.INSTALL.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Group.PACKAGE.name + variant.name ).name

		return t
	}
}
