package flair.gradle.tasks.factory

import flair.gradle.tasks.Group
import flair.gradle.tasks.Install
import flair.gradle.tasks.TaskManager
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class InstallTaskFactory implements VariantTaskFactory<Install>
{
	public Install create( Project project , Variant variant )
	{
		String name = "install" + variant.name

		Install t = project.tasks.findByName( name ) as Install

		if( !t ) t = project.tasks.create( name , Install )

		t.group = Group.INSTALL.name
		t.variant = variant
		t.dependsOn TaskManager.getVariantTask( project , Group.PACKAGE , variant )

		return t
	}
}
