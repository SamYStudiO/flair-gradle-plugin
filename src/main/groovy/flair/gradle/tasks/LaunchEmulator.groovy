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
class LaunchEmulator extends AbstractVariantTask
{
	private ICli cli = new Adl( )

	public LaunchEmulator()
	{
		group = Groups.LAUNCH.name
		description = ""
	}

	@TaskAction
	public void launch()
	{
		String output = "${ project.buildDir }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }"

		cli.addArgument( "-profile" )

		cli.addArgument( variant.platform == Platforms.DESKTOP ? "extendedDesktop" : "mobileDevice" )

		if( variant.platform != Platforms.DESKTOP )
		{
			cli.addArgument( "-screensize" )
			cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperties.EMULATOR_SCREEN_SIZE ).toString( ) )
			cli.addArgument( "-XscreenDPI" )
			cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperties.EMULATOR_SCREEN_DPI ).toString( ) )
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

		cli.execute( project )

		f.delete( )
	}
}
