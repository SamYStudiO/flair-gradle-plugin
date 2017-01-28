package flair.gradle.plugins

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.tasks.ListDevices
import flair.gradle.tasks.TaskDefinition
import flair.gradle.tasks.variantFactories.IVariantTaskFactory
import flair.gradle.tasks.variantFactories.LaunchDeviceTaskFactory
import flair.gradle.tasks.variantFactories.UninstallTaskFactory
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
abstract class AbstractMobilePlatformPlugin extends AbstractPlatformPlugin
{
	@Override
	void apply( Project project )
	{
		super.apply( project )

		project.afterEvaluate {

			if( !project.file( ( project.flair as IExtensionManager ).getFlairProperty( FlairProperty.MODULE_NAME ) ).exists( ) )
			{
				project.tasks.remove( project.tasks.getByName( TaskDefinition.LIST_DEVICES.name + platform.name.capitalize( ) ) )
			}
		}
	}

	@Override
	void addTasks()
	{
		ListDevices task = project.tasks.create( TaskDefinition.LIST_DEVICES.name + platform.name.capitalize( ) , TaskDefinition.LIST_DEVICES.type ) as ListDevices
		task.platform = platform
	}

	@Override
	List<IVariantTaskFactory> getVariantTaskFactories()
	{
		List<IVariantTaskFactory> list = super.variantTaskFactories

		list.add( new UninstallTaskFactory( ) )
		list.add( new LaunchDeviceTaskFactory( ) )

		return list
	}
}
