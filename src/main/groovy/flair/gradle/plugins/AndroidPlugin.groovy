package flair.gradle.plugins

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.structures.AndroidStructure
import flair.gradle.structures.IStructure
import flair.gradle.tasks.ListDevices
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AndroidPlugin extends AbstractPlatformPlugin
{
	public AndroidPlugin()
	{
		platform = Platforms.ANDROID
	}

	@Override
	public void apply( Project project )
	{
		super.apply( project )

		project.afterEvaluate {

			if( !project.file( ( project.flair as IExtensionManager ).getFlairProperty( FlairProperties.MODULE_NAME.name ) ).exists( ) )
			{
				project.tasks.remove( project.tasks.getByName( Tasks.LIST_DEVICES.name + platform.name.capitalize( ) ) )
			}
		}
	}

	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new AndroidStructure( ) )

		return list
	}

	@Override
	public void addTasks()
	{
		ListDevices task = project.tasks.create( Tasks.LIST_DEVICES.name + platform.name.capitalize( ) , Tasks.LIST_DEVICES.type ) as ListDevices
		task.platform = platform
	}
}
