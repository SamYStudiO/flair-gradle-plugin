package flair.gradle.tasks

import flair.gradle.cli.Adb
import flair.gradle.cli.ICli
import flair.gradle.cli.Idb
import flair.gradle.utils.Platform
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ListDevices extends AbstractTask
{
	private ICli idb = new Idb( )

	private ICli adb = new Adb( )

	private Platform platform

	Platform getPlatform()
	{
		return platform
	}

	void setPlatform( Platform platform )
	{
		this.platform = platform
		description = "Displays ${ platform.name } connected devices"
	}

	ListDevices()
	{
		group = TaskGroup.DEVICES.name
	}

	@TaskAction
	void list()
	{
		if( platform == Platform.IOS )
		{
			idb.addArgument( "-devices" )
			idb.execute( project , Platform.IOS )
		}
		else if( platform == Platform.ANDROID )
		{
			adb.addArgument( "devices" )
			adb.execute( project , Platform.ANDROID )
		}
	}
}
