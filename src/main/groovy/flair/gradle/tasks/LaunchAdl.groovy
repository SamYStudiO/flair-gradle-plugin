package flair.gradle.tasks

import flair.gradle.cli.Adl
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class LaunchAdl extends AbstractVariantTask
{
	private ICli cli = new Adl( )

	public LaunchAdl()
	{
		group = Groups.LAUNCH.name
		description = ""
	}

	@TaskAction
	public void launch()
	{
		String pubId = extensionManager.getFlairProperty( variant , FlairProperties.ADL_PUB_ID )
		boolean noDebug = extensionManager.getFlairProperty( variant , FlairProperties.ADL_NO_DEBUG )
		boolean atLogin = extensionManager.getFlairProperty( variant , FlairProperties.ADL_AT_LOGIN )
		List<String> parameters = extensionManager.getFlairProperty( variant , FlairProperties.ADL_PARAMETERS ) as List<String>

		cli.addArgument( "-profile" )
		cli.addArgument( variant.platform == Platforms.DESKTOP ? "extendedDesktop" : "mobileDevice" )

		if( pubId ) cli.addArgument( "-pubid ${ pubId }" )
		if( noDebug ) cli.addArgument( "-noDebug" )
		if( atLogin ) cli.addArgument( "-atlogin" )

		if( variant.platform != Platforms.DESKTOP )
		{
			cli.addArgument( "-screensize" )
			cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperties.ADL_SCREEN_SIZE ).toString( ) )
			cli.addArgument( "-XscreenDPI" )
			cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperties.ADL_SCREEN_DPI ).toString( ) )
			cli.addArgument( "-XversionPlatform" )
			cli.addArgument( variant.platform == Platforms.IOS ? "IOS" : "AND" )
		}

		cli.addArgument( "-extdir" )
		cli.addArgument( project.file( outputVariantDir.path + "/extracted_extensions" ).path )

		cli.addArgument( "${ outputVariantDir.path }/package/app_descriptor.xml" )
		cli.addArgument( project.file( "${ outputVariantDir.path }/package" ).path )

		if( parameters && !parameters.empty )
		{
			cli.addArgument( "--" )
			cli.addArguments( parameters )
		}

		cli.execute( project )
	}
}
