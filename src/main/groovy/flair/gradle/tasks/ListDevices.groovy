package flair.gradle.tasks

import flair.gradle.cli.Adb
import flair.gradle.cli.ICli
import flair.gradle.cli.Idb
import flair.gradle.variants.Platforms
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ListDevices extends AbstractTask
{
	private ICli idb = new Idb( )

	private ICli adb = new Adb( )

	public Platforms platform

	public ListDevices()
	{
		group = Groups.DEVICES.name
		description = ""
	}

	@TaskAction
	public void list()
	{
		if( platform == Platforms.IOS )
		{
			idb.addArgument( "-devices" )
			idb.execute( project )
		}
		else if( platform == Platforms.ANDROID )
		{
			adb.addArgument( "devices" )
			adb.execute( project )
		}
	}
}
