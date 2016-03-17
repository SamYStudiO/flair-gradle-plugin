package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.process.ProcessSplashs
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSplashsTaskFactory implements IVariantTaskFactory<ProcessSplashs>
{
	public ProcessSplashs create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_SPLASHS.name + variant.getName( Variant.NamingType.CAPITALIZE )

		ProcessSplashs t = project.tasks.findByName( name ) as ProcessSplashs

		if( !t ) t = project.tasks.create( name , ProcessSplashs )

		t.group = TaskDefinition.PROCESS_SPLASHS.group.name
		t.variant = variant

		return t
	}
}
