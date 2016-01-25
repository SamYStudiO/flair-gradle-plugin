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
		String name = Tasks.LAUNCH_EMULATOR.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )

		LaunchEmulator t = project.tasks.findByName( name ) as LaunchEmulator

		if( !t ) t = project.tasks.create( name , LaunchEmulator )

		t.group = Tasks.LAUNCH_EMULATOR.group.name
		t.variant = variant
		t.dependsOn project.tasks.getByName( Tasks.COMPILE.name + variant.getNameWithType( Variant.NamingTypes.CAPITALIZE ) ).name

		return t
	}
}
