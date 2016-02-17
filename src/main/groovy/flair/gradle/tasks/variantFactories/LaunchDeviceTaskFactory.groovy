package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.LaunchDevice
import flair.gradle.tasks.TaskDefinition
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchDeviceTaskFactory implements IVariantTaskFactory<LaunchDevice>
{
	public LaunchDevice create( Project project , Variant variant )
	{
		String variantName = variant.getNameWithType( Variant.NamingTypes.CAPITALIZE )
		String name = TaskDefinition.LAUNCH_DEVICE.name + variantName

		LaunchDevice t = project.tasks.findByName( name ) as LaunchDevice

		if( !t ) t = project.tasks.create( name , LaunchDevice )

		t.group = TaskDefinition.LAUNCH_DEVICE.group.name
		t.variant = variant
		t.dependsOn TaskDefinition.INSTALL.name + variantName

		return t
	}
}
