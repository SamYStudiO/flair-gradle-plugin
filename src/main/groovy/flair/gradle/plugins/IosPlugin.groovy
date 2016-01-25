package flair.gradle.plugins

import flair.gradle.structures.IStructure
import flair.gradle.structures.IosStructure
import flair.gradle.tasks.ListDevices
import flair.gradle.tasks.Tasks
import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IosPlugin extends AbstractPlatformPlugin
{
	public IosPlugin()
	{
		platform = Platforms.IOS
	}

	@Override
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new IosStructure( ) )

		return list
	}

	@Override
	public void addTasks()
	{
		ListDevices task = project.tasks.create( Tasks.LIST_DEVICES.name + platform.name.capitalize(  ) , Tasks.LIST_DEVICES.type ) as ListDevices
		task.platform = platform
	}
}
