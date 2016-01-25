package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.Properties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Package extends AbstractVariantTask
{
	private ICli cli = new Adt( )

	private String input

	private String output

	public Package()
	{
		group = Groups.PACKAGE.name
		description = ""
	}

	@TaskAction
	public void packaging()
	{
		input = "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }"
		output = project.buildDir

		cli.clearArguments( )
		cli.addArgument( "-package" )

		addArchitecture( )
		addTarget( )
		addSigning( )
		addOutput( )
		addFilesAndDirectories( )
		addExtensionNatives( )

		cli.execute( project )
	}

	private addArchitecture()
	{
		if( variant.platform == Platforms.ANDROID )
		{
			cli.addArgument( "-arch" )
			cli.addArgument( extensionManager.getFlairProperty( variant , Properties.X86.name ) ? "x86" : "armv7" )
		}
	}

	private addTarget()
	{
		cli.addArgument( "-target" )

		String target = extensionManager.getFlairProperty( variant , Properties.PACKAGE_TARGET.name )

		switch( variant.platform )
		{
			case Platforms.IOS:

				switch( target )
				{
					case "ipa-ad-hoc":
					case "ipa-app-store":
					case "ipa-debug":
					case "ipa-test":

						cli.addArgument( target )
						break

					default:

						if( extensionManager.getFlairProperty( variant , Properties.DEBUG.name ) ) cli.addArgument( "ipa-debug" ) else cli.addArgument( "ipa-test" )
						break
				}

				break

			case Platforms.ANDROID:

				switch( target )
				{
					case "apk-captive-runtime":
					case "apk-debug":
					case "apk-emulator":
					case "apk-profile":

						cli.addArgument( target )
						break

					default:
						if( extensionManager.getFlairProperty( variant , Properties.DEBUG.name ) ) cli.addArgument( "apk-debug" ) else cli.addArgument( "apk-captive-runtime" )
						break
				}

				break

			case Platforms.DESKTOP:

				if( target == "native" ) cli.addArgument( "native" ) else cli.addArgument( "air" )

				break
		}
	}

	private addSigning()
	{
		cli.addArgument( "-storetype" )
		cli.addArgument( "pkcs12" )
		cli.addArgument( "-keystore" )
		cli.addArgument( project.file( "app/src/android/signing/certificate.p12" ).path )
		cli.addArgument( "-storepass" )
		cli.addArgument( "0000" )
	}

	private addOutput()
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

				String target = extensionManager.getFlairProperty( variant , Properties.PACKAGE_TARGET.name )

				if( target == "native" )
				{

					if( Os.isFamily( Os.FAMILY_MAC ) ) extension = "dmg" else if( Os.isFamily( Os.FAMILY_WINDOWS ) ) extension = "exe" else extension = "deb"
				}
				else extension = "air"
				break
		}

		cli.addArgument( project.file( "${ output }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.${ extension }" ).path )
	}

	private addFilesAndDirectories()
	{
		cli.addArgument( project.file( "${ input }/app_descriptor.xml" ).path )

		cli.addArgument( "-C" )
		cli.addArgument( project.file( input ).path )
		cli.addArgument( "icons" )
		cli.addArgument( "resources" )
		cli.addArgument( "${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" )

		project.file( input ).listFiles( ).each {

			if( it.name.indexOf( ".png" ) > 0 ) cli.addArgument( it.name )
		}
	}

	private addExtensionNatives()
	{
		File f = project.file( "${ input }/extensions" )

		if( f.exists( ) && f.listFiles( ).size( ) > 0 )
		{
			cli.addArgument( "-extdir" )
			cli.addArgument( project.file( "${ input }/extensions" ).path )
		}
	}
}
