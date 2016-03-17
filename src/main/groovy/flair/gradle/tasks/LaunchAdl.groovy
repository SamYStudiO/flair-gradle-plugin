package flair.gradle.tasks

import flair.gradle.cli.Adl
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperty
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class LaunchAdl extends AbstractVariantTask
{
	private ICli cli = new Adl( )

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		description = "Launches ${ variant.name } using ADL emulator"
	}

	public LaunchAdl()
	{
		group = TaskGroup.LAUNCH.name
	}

	@TaskAction
	public void launch()
	{
		String pubId = extensionManager.getFlairProperty( variant , FlairProperty.ADL_PUB_ID )
		boolean noDebug = extensionManager.getFlairProperty( variant , FlairProperty.ADL_NO_DEBUG )
		boolean atLogin = extensionManager.getFlairProperty( variant , FlairProperty.ADL_AT_LOGIN )
		List<String> parameters = extensionManager.getFlairProperty( variant , FlairProperty.ADL_PARAMETERS ) as List<String>

		cli.addArgument( "-profile" )
		cli.addArgument( variant.platform == Platform.DESKTOP ? "extendedDesktop" : "mobileDevice" )

		if( pubId ) cli.addArgument( "-pubid ${ pubId }" )
		if( noDebug ) cli.addArgument( "-noDebug" )
		if( atLogin ) cli.addArgument( "-atlogin" )

		if( variant.platform != Platform.DESKTOP )
		{
			cli.addArgument( "-screensize" )
			cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperty.ADL_SCREEN_SIZE ).toString( ) )
			cli.addArgument( "-XscreenDPI" )
			cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperty.ADL_SCREEN_DPI ).toString( ) )
			cli.addArgument( "-XversionPlatform" )
			cli.addArgument( variant.platform == Platform.IOS ? "IOS" : "AND" )
		}

		cli.addArgument( "-extdir" )
		cli.addArgument( project.file( outputVariantDir.path + "/extracted_extensions" ).path )

		cli.addArgument( "${ outputVariantDir.path }/package/app_descriptor.xml" )
		cli.addArgument( project.file( "${ outputVariantDir.path }/package" ).path )

		if( parameters.size(  ) )
		{
			cli.addArgument( "--" )
			cli.addArguments( parameters )
		}

		cli.execute( project , variant.platform )
	}
}
