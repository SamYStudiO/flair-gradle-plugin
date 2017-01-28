package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.processes.ProcessSplashScreens
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSplashScreensTaskFactory implements IVariantTaskFactory<ProcessSplashScreens>
{
	ProcessSplashScreens create( Project project , Variant variant )
	{
		String name = TaskDefinition.PROCESS_SPLASH_SCREENS.name + variant.getName( Variant.NamingType.CAPITALIZE )

		ProcessSplashScreens t = project.tasks.findByName( name ) as ProcessSplashScreens

		if( !t ) t = project.tasks.create( name , ProcessSplashScreens )

		t.group = TaskDefinition.PROCESS_SPLASH_SCREENS.group.name
		t.variant = variant

		return t
	}
}
