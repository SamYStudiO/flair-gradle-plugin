package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperty
import flair.gradle.utils.CliDevicesOutputParser
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchDevice extends AbstractVariantTask
{
	private ICli adt = new Adt( )

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		description = "Launches ${ variant.name } from first connect device"
	}

	public LaunchDevice()
	{
		group = TaskGroup.LAUNCH.name
	}

	@TaskAction
	public void launch()
	{
		String platformSdk = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_PLATFORM_SDK )
		String appId = extensionManager.getFlairProperty( variant , FlairProperty.APP_ID ) + extensionManager.getFlairProperty( variant , FlairProperty.APP_ID_SUFFIX )

		adt.addArgument( "-devices" )
		adt.addArgument( "-platform ${ variant.platform.name }" )
		String id = new CliDevicesOutputParser( ).parse( adt.execute( project , variant.platform ) )
		String deviceId = !id && platformSdk && variant.platform == Platform.IOS ? "ios_simulator" : id

		if( deviceId )
		{
			adt.clearArguments( )
			adt.addArgument( "-launchApp" )
			adt.addArgument( "-platform ${ variant.platform.name }" )
			if( platformSdk ) adt.addArgument( "-platformsdk ${ platformSdk }" )

			adt.addArgument( "-device ${ deviceId }" )
			adt.addArgument( "-appid ${ appId }" )
			adt.execute( project , variant.platform )
		}
		else println( "No device detected" )
	}
}
