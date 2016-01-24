package flair.gradle.tasks

import flair.gradle.cli.Adl
import flair.gradle.cli.ICli
import flair.gradle.extensions.Extensions
import flair.gradle.extensions.Properties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class LaunchAdl extends AbstractVariantTask
{
	ICli cli = new Adl( )

	public LaunchAdl()
	{
		group = Groups.LAUNCH.name
		description = ""
	}

	@TaskAction
	public void launch()
	{
		cli.reset( )

		String output = "${ project.buildDir }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }"

		cli.addArgument( "-profile" )
		cli.addArgument( variant.platform == Platforms.DESKTOP ? "extendedDesktop" : "mobileDevice" )
		cli.addArgument( "-screensize" )
		cli.addArgument( extensionManager.getFlairProperty( variant , Properties.EMULATOR_SCREEN_SIZE.name ).toString( ) )
		cli.addArgument( "-XscreenDPI" )
		cli.addArgument( extensionManager.getFlairProperty( variant , Properties.EMULATOR_SCREEN_DPI.name ).toString( ) )

		cli.addArgument( project.file( output + "/app_descriptor.xml" ).path )
		cli.addArgument( project.file( output ).path )

		cli.arguments.each { arg -> println( arg ) }
		cli.execute( project )
	}
}
