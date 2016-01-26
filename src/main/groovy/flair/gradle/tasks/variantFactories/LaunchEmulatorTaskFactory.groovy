package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.LaunchEmulator
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchEmulatorTaskFactory implements IVariantTaskFactory<LaunchEmulator>
{
	public LaunchEmulator create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = Tasks.LAUNCH_EMULATOR.name + variantName

		LaunchEmulator t = project.tasks.findByName( name ) as LaunchEmulator

		if( !t ) t = project.tasks.create( name , LaunchEmulator )

		t.group = Tasks.LAUNCH_EMULATOR.group.name
		t.variant = variant
		t.dependsOn Tasks.COMPILE.name + variantName , Tasks.PROCESS_EXTENSIONS.name + variantName ,
				Tasks.PROCESS_RESOURCES.name + variantName , Tasks.PROCESS_ASSETS.name + variantName , Tasks.PROCESS_ICONS.name + variantName ,
				Tasks.PROCESS_SPLASHS.name + variantName

		return t
	}
}
