package flair.gradle.tasks

import flair.gradle.cli.Adl
import flair.gradle.cli.ICli
import flair.gradle.extensions.Properties
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
			cli.addArgument( extensionManager.getFlairProperty( variant , Properties.EMULATOR_SCREEN_SIZE.name ).toString( ) )
			cli.addArgument( "-XscreenDPI" )
			cli.addArgument( extensionManager.getFlairProperty( variant , Properties.EMULATOR_SCREEN_DPI.name ).toString( ) )
		}

		cli.addArgument( "-extdir" )
		cli.addArgument( project.file( output + "/extracted_extensions" ).path )
		cli.addArgument( project.file( output + "/app_descriptor.xml" ).path )
		cli.addArgument( project.file( output ).path )

		cli.execute( project )

		project.file( output + "/extracted_extensions" ).deleteDir( )
	}
}
