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
		String output = "${ project.buildDir }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }"
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
		cli.addArgument( project.file( output + "/extracted_extensions" ).path )

		File f = project.file( output + "/package/app_descriptor.xml" )

		project.copy {
			from output + "/app_descriptor.xml"
			into output + "/package"
		}

		cli.addArgument( f.path )
		cli.addArgument( project.file( output + "/package" ).path )

		if( parameters && !parameters.empty )
		{
			cli.addArgument( "--" )
			cli.addArguments( parameters )
		}

		cli.execute( project )

		f.delete( )
	}
}
