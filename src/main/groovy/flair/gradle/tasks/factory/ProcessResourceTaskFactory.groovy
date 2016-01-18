package flair.gradle.tasks.factory

import flair.gradle.tasks.Group
import flair.gradle.tasks.others.ProcessResources
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessResourceTaskFactory implements VariantTaskFactory<ProcessResources>
{
	public ProcessResources create( Project project , Variant variant )
	{
		String name = "process" + variant.name

		ProcessResources t = project.tasks.findByName( name ) as ProcessResources

		if( !t ) t = project.tasks.create( name , ProcessResources )

		t.group = Group.DEFAULT.name
		t.variant = variant

		return t
	}
}
