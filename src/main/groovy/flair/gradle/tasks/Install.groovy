package flair.gradle.tasks

import flair.gradle.cli.Adb
import flair.gradle.cli.ICli
import flair.gradle.cli.Idb
import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Install extends AbstractVariantTask
{
	private ICli adb = new Adb( )

	private ICli idb = new Idb( )

	public Install()
	{
		group = Groups.INSTALL.name
		description = ""
	}

	@TaskAction
	public void install()
	{
		String path = "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }_${ extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION ) }.${ getExtension( ) }"

		if( variant.platform == Platforms.DESKTOP )
		{
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream( )

			project.exec {
				executable project.file( path ).path

				ignoreExitValue = true
				standardOutput = outputStream
			}

			println( outputStream.toString( ) )
		}
		else if( variant.platform == Platforms.ANDROID )
		{

			adb.addArgument( "install" )
			adb.addArgument( "-r" )
			adb.addArgument( project.file( path ).path )

			adb.execute( project )
		}
		else
		{
			idb.addArgument( "-install" )
			idb.addArgument( project.file( path ).path )
			//idb.addArgument( device )

			idb.execute( project )
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
