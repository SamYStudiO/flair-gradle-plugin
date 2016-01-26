package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.Tasks
import flair.gradle.tasks.process.ProcessIcons
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessIconsTaskFactory implements IVariantTaskFactory<ProcessIcons>
{
	public ProcessIcons create( Project project , Variant variant )
	{
		String name = Tasks.PROCESS_ICONS.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		ProcessIcons t = project.tasks.findByName( name ) as ProcessIcons

		if( !t ) t = project.tasks.create( name , ProcessIcons )

		t.group = Tasks.PROCESS_ICONS.group.name
		t.variant = variant

		return t
	}
}
