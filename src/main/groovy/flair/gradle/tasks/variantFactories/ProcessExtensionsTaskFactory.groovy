package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Tasks
import flair.gradle.tasks.process.ProcessExtensions
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessExtensionsTaskFactory implements IVariantTaskFactory<ProcessExtensions>
{
	public ProcessExtensions create( Project project , Variant variant )
	{
		String name = Tasks.PROCESS_EXTENSIONS.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		ProcessExtensions t = project.tasks.findByName( name ) as ProcessExtensions

		if( !t ) t = project.tasks.create( name , ProcessExtensions )

		t.group = Tasks.PROCESS_EXTENSIONS.group.name
		t.variant = variant

		return t
	}
}
