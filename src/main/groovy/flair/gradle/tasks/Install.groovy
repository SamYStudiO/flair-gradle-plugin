package flair.gradle.tasks

import flair.gradle.cli.Adb
import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperties
import flair.gradle.utils.CliDevicesOutputParser
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

	private ICli adt = new Adt( )

	public Install()
	{
		group = Groups.INSTALL.name
		description = ""
	}

	@TaskAction
	public void install()
	{
		String path = "${ outputVariantDir }/package/${ project.name }_${ variant.getProductFlavorsBuildTypeWithType( Variant.NamingTypes.UNDERSCORE ) }_${ extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION ) }.${ getExtension( ) }"

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
			adb.addArgument( "devices" )
			String id = new CliDevicesOutputParser( ).parse( adb.execute( project ) )

			if( id )
			{
				adb.clearArguments( )
				adb.addArgument( "-s ${ id }" )
				adb.addArgument( "install" )
				adb.addArgument( "-r" )
				adb.addArgument( project.file( path ).path )

				adb.execute( project )
			}
			else println( "No device detected" )
		}
		else
		{
			String platformSdk = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_PLATFORM_SDK )

			adt.addArgument( "-devices" )
			adt.addArgument( "-platform ios" )
			String id = new CliDevicesOutputParser( ).parse( adt.execute( project ) )
			id = !id && platformSdk ? "ios_simulator" : id

			if( id )
			{
				adt.clearArguments( )
				adt.addArgument( "-installApp" )
				adt.addArgument( "-platform ios" )
				if( platformSdk ) adt.addArgument( "-platformsdk ${ platformSdk }" )
				adt.addArgument( "-device ${ id }" )
				adt.addArgument( project.file( path ).path )
				adt.execute( project )
			}
			else println( "No device detected" )
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
