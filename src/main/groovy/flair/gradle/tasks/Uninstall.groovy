package flair.gradle.tasks

import flair.gradle.cli.Adb
import flair.gradle.cli.ICli
import flair.gradle.cli.Idb
import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Platforms
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Uninstall extends AbstractVariantTask
{
	private ICli adb = new Adb( )

	private ICli idb = new Idb( )

	public Uninstall()
	{
		group = Groups.UNINSTALL.name
		description = ""
	}

	@TaskAction
	public void uninstall()
	{
		String appId = extensionManager.getFlairProperty( variant , FlairProperties.APP_ID ) + extensionManager.getFlairProperty( variant , FlairProperties.APP_ID_SUFFIX )

		if( variant.platform == Platforms.IOS )
		{

			idb.addArgument( "-uninstall" )
			idb.addArgument( appId )
			//idb.addArgument( device )

			idb.execute( project )
		}
		else
		{
			adb.addArgument( "uninstall" )
			adb.addArgument( appId )

			adb.execute( project )
		}
	}

	private String getExtension()
	{
		String extension = ""

		switch( variant.platform )
		{
			case Platforms.IOS:
				extension = "ipa"
				break

			case Platforms.ANDROID:
				extension = "apk"
				break

			case Platforms.DESKTOP:

				if( Os.isFamily( Os.FAMILY_MAC ) ) extension = "dmg" else if( Os.isFamily( Os.FAMILY_WINDOWS ) ) extension = "exe" else extension = "deb"
				break
		}

		return extension
	}
}
