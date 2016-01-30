package flair.gradle.plugins

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.tasks.ListDevices
import flair.gradle.tasks.Tasks
import flair.gradle.tasks.variantFactories.IVariantTaskFactory
import flair.gradle.tasks.variantFactories.LaunchDeviceTaskFactory
import flair.gradle.tasks.variantFactories.UninstallTaskFactory
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractMobilePlatformPlugin extends AbstractPlatformPlugin
{
	@Override
	public void apply( Project project )
	{
		super.apply( project )

		project.afterEvaluate {

			if( !project.file( ( project.flair as IExtensionManager ).getFlairProperty( FlairProperties.MODULE_NAME ) ).exists( ) )
			{
				project.tasks.remove( project.tasks.getByName( Tasks.LIST_DEVICES.name + platform.name.capitalize( ) ) )
			}
		}
	}

	@Override
	public void addTasks()
	{
		ListDevices task = project.tasks.create( Tasks.LIST_DEVICES.name + platform.name.capitalize( ) , Tasks.LIST_DEVICES.type ) as ListDevices
		task.platform = platform
	}

	@Override
	public List<IVariantTaskFactory> getVariantTaskFactories()
	{
		List<IVariantTaskFactory> list = super.variantTaskFactories

		list.add( new UninstallTaskFactory( ) )
		list.add( new LaunchDeviceTaskFactory( ) )

		return list
	}
}
