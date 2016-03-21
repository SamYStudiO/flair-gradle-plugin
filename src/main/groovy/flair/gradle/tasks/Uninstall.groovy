package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperty
import flair.gradle.utils.CliDevicesOutputParser
import flair.gradle.utils.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Uninstall extends VariantTask
{
	private ICli adt = new Adt( )

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		description = "Uninstalls ${ variant.name } from first connect device"
	}

	public Uninstall()
	{
		group = TaskGroup.UNINSTALL.name
	}

	@TaskAction
	public void uninstall()
	{
		String appId = extensionManager.getFlairProperty( variant , FlairProperty.APP_ID ) + extensionManager.getFlairProperty( variant , FlairProperty.APP_ID_SUFFIX )
		String platformSdk = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_PLATFORM_SDK )

		adt.addArgument( "-devices" )
		adt.addArgument( "-platform ${ variant.platform.name }" )
		String id = new CliDevicesOutputParser( ).parse( adt.execute( project , variant.platform ) )
		id = !id && platformSdk ? "ios_simulator" : id

		if( id )
		{
			adt.clearArguments( )
			adt.addArgument( "-uninstallApp" )
			adt.addArgument( "-platform ${ variant.platform.name }" )
			if( platformSdk ) adt.addArgument( "-platformsdk ${ platformSdk }" )
			adt.addArgument( "-device ${ id }" )
			adt.addArgument( "-appid ${ appId }" )

			adt.execute( project , variant.platform )
		}
		else println( "No device detected" )
	}
}
