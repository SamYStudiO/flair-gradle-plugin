package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Tasks
import flair.gradle.tasks.process.ProcessSplashs
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSplashsTaskFactory implements IVariantTaskFactory<ProcessSplashs>
{
	public ProcessSplashs create( Project project , Variant variant )
	{
		String name = Tasks.PROCESS_SPLASHS.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		ProcessSplashs t = project.tasks.findByName( name ) as ProcessSplashs

		if( !t ) t = project.tasks.create( name , ProcessSplashs )

		t.group = Tasks.PROCESS_SPLASHS.group.name
		t.variant = variant

		return t
	}
}
