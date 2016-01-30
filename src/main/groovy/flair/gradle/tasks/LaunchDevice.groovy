package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperties
import flair.gradle.utils.CliDevicesOutputParser
import flair.gradle.variants.Platforms
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchDevice extends AbstractVariantTask
{
	private ICli adt = new Adt( )

	public LaunchDevice()
	{
		group = Groups.LAUNCH.name
		description = ""
	}

	@TaskAction
	public void launch()
	{
		String platformSdk = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_PLATFORM_SDK )
		String appId = extensionManager.getFlairProperty( variant , FlairProperties.APP_ID ) + extensionManager.getFlairProperty( variant , FlairProperties.APP_ID_SUFFIX )


		adt.addArgument( "-devices" )
		adt.addArgument( "-platform ${ variant.platform.name }" )
		List<String> ids = new CliDevicesOutputParser( ).parse( adt.execute( project ) )
		String deviceId = ids.empty && platformSdk && variant.platform == Platforms.IOS ? "ios_simulator" : ids[ 0 ]

		adt.clearArguments( )
		adt.addArgument( "-launchApp" )
		adt.addArgument( "-platform ${ variant.platform.name }" )
		if( platformSdk ) adt.addArgument( "-platformsdk ${ platformSdk }" )

		adt.addArgument( "-device ${ deviceId }" )
		adt.addArgument( "-appi ${ appId }" )
		adt.execute( project )
	}
}
