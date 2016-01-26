package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Package extends AbstractVariantTask
{
	protected ICli cli = new Adt( )

	@InputFiles
	def Set<File> inputDirs

	@OutputFile
	def File packageFile

	@Input
	def String connect

	@Input
	def String listen

	@Input
	def String sampler

	@Input
	def String hideAneLibSymbols

	@Input
	def String x86

	@Input
	def String target

	@Input
	def String debug

	@Input
	def String version

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputDirs = getInputFiles( )
		packageFile = project.file( "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }_${ version }.${ getExtension( ) }" )

		connect = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_CONNECT.name )
		listen = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_LISTEN.name )
		sampler = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_SAMPLER.name ) as List<String>
		hideAneLibSymbols = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_HIDE_ANE_LIB_SYMBOLS.name ) as List<String>
		x86 = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_X86.name ) as List<String>
		target = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_TARGET.name ) as List<String>
		debug = extensionManager.getFlairProperty( variant , FlairProperties.DEBUG.name ) as List<String>
		version = extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION.name ) as List<String>
	}

	public Package()
	{
		group = Groups.PACKAGE.name
		description = ""
	}

	@TaskAction
	public void packaging()
	{
		cli.clearArguments( )

		cli.addArgument( "-package" )

		if( variant.platform == Platforms.DESKTOP ) addSigning( )
		addTarget( )
		if( variant.platform != Platforms.DESKTOP )
		{
			if( connect != null ) addConnect( ) else if( listen != null ) addListen( )
		}
		if( variant.platform == Platforms.IOS )
		{
			if( sampler ) addSampler( )
			if( hideAneLibSymbols ) addHideAneLibSymbols( )
		}
		if( variant.platform == Platforms.ANDROID ) addArchitecture( )
		if( variant.platform != Platforms.DESKTOP ) addSigning( )
		addOutput( )
		addFilesAndDirectories( )
		addExtensionNatives( )

		cli.execute( project )
	}

	private addArchitecture()
	{
		cli.addArgument( "-arch" )
		cli.addArgument( x86 ? "x86" : "armv7" )
	}

	private addTarget()
	{
		cli.addArgument( "-target" )

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

						if( debug ) cli.addArgument( "ipa-debug" ) else cli.addArgument( "ipa-test" )
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
						if( debug ) cli.addArgument( "apk-debug" ) else cli.addArgument( "apk-captive-runtime" )
						break
				}

				break

			case Platforms.DESKTOP:

				cli.addArgument( "native" )

				break
		}
	}

	private addSigning()
	{
		// TODO integrate signing properly
		cli.addArgument( "-storetype" )
		cli.addArgument( "pkcs12" )
		cli.addArgument( "-keystore" )
		cli.addArgument( project.file( "app/src/android/signing/certificate.p12" ).path )
		cli.addArgument( "-storepass" )
		cli.addArgument( "0000" )
	}

	private addConnect()
	{
		cli.addArgument( "-connect" )
		if( connect != "" ) cli.addArgument( connect )
	}

	private addListen()
	{
		cli.addArgument( "-listen" )
		if( listen != "" ) cli.addArgument( listen )
	}

	private addSampler()
	{
		cli.addArgument( "-sampler" )
	}

	private addHideAneLibSymbols()
	{
		cli.addArgument( "-hideAneLibSymbols" )
		cli.addArgument( "yes" )
	}

	private addOutput()
	{
		cli.addArgument( packageFile.path )
	}

	private addFilesAndDirectories()
	{
		cli.addArgument( project.file( "${ outputVariantDir.path }/app_descriptor.xml" ).path )
		cli.addArgument( "-C" )
		cli.addArgument( outputVariantDir.path )

		outputVariantDir.listFiles( ).each { cli.addArgument( it.name ) }
	}

	private addExtensionNatives()
	{
		File f = project.file( "${ outputVariantDir.path }/extensions" )

		if( f.exists( ) && f.listFiles( ).size( ) > 0 )
		{
			cli.addArgument( "-extdir" )
			cli.addArgument( project.file( "${ outputVariantDir.path }/extensions" ).path )
		}
	}

	private getExtension()
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

	private Set<File> getInputFiles()
	{
		Set<File> set = new HashSet<File>( )

		set.add( project.file( "${ outputVariantDir.path }/extensions" ) )
		set.add( project.file( "${ outputVariantDir.path }/package" ) )
		set.add( project.file( "${ outputVariantDir.path }/app_descriptor" ) )

		return set
	}
}
