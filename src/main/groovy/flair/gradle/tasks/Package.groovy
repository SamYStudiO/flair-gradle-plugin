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
	def Set<File> inputFiles

	@OutputFile
	def File outputFile

	@Input
	def String connect

	@Input
	def String listen

	@Input
	def boolean sampler

	@Input
	def boolean hideAneLibSymbols

	@Input
	def boolean x86

	@Input
	def String target

	@Input
	def String platformSdk

	@Input
	def boolean debug

	@Input
	def String version

	@Input
	def String alias

	@Input
	def String provider

	@Input
	def String storeType

	@Input
	def String storePass

	@Input
	def String keyStore

	@Input
	def String keyPass

	@Input
	def String tsa

	@Input
	def String provisioning

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )

		connect = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_CONNECT ) ?: "null"
		listen = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_LISTEN ) ?: "null"
		sampler = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_SAMPLER )
		hideAneLibSymbols = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_HIDE_ANE_LIB_SYMBOLS )
		x86 = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_X86 )
		target = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_TARGET ) ?: "null"
		platformSdk = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_PLATFORM_SDK ) ?: "null"
		version = extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION )
		debug = extensionManager.getFlairProperty( variant , FlairProperties.DEBUG )

		alias = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_ALIAS ) ?: "null"
		provider = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_PROVIDER_NAME ) ?: "null"
		storeType = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_STORE_TYPE ) ?: "null"
		storePass = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_STORE_PASS ) ?: "null"
		keyStore = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_KEY_STORE ) ?: "null"
		keyPass = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_KEY_PASS ) ?: "null"
		tsa = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_TSA ) ?: "null"
		provisioning = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_PROVISIONING_PROFILE ) ?: "null"

		outputFile = project.file( "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }_${ version }.${ getExtension( ) }" )
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
			if( connect != "null" ) addConnect( ) else if( listen != "null" ) addListen( )
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
		cli.addArgument( target )
	}

	private addSigning()
	{

		if( alias != "null" )
		{
			cli.addArgument( "-alias" )
			cli.addArgument( alias )
		}

		if( provider != "null" )
		{
			cli.addArgument( "-providerName" )
			cli.addArgument( provider )
		}

		if( storeType != "null" )
		{
			cli.addArgument( "-storetype" )
			cli.addArgument( storeType )
		}

		if( storePass != "null" )
		{
			cli.addArgument( "-storepass" )
			cli.addArgument( storePass )
		}

		if( keyStore != "null" )
		{
			cli.addArgument( "-keystore" )
			cli.addArgument( keyStore )
		}

		if( keyPass != "null" )
		{
			cli.addArgument( "-keypass" )
			cli.addArgument( keyPass )
		}

		if( tsa != "null" )
		{
			cli.addArgument( "-tsa" )
			cli.addArgument( tsa )
		}

		if( provisioning != "null" )
		{
			cli.addArgument( "-provisioning-profile" )
			cli.addArgument( provisioning )
		}
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
		cli.addArgument( outputFile.path )
	}

	private addFilesAndDirectories()
	{
		cli.addArgument( project.file( "${ outputVariantDir.path }/app_descriptor.xml" ).path )
		if( platformSdk != "null" ) cli.addArgument( "-platformsdk ${ platformSdk }" )
		cli.addArgument( "-C" )
		cli.addArgument( project.file( "${ outputVariantDir.path }/package" ).path )

		project.file( "${ outputVariantDir.path }/package" ).listFiles( ).each {
			if( it.name != "app_descriptor.xml" && it.name.indexOf( ".apk" ) < 0 && it.name.indexOf( ".ipa" ) < 0 && it.name.indexOf( ".exe" ) < 0 && it.name.indexOf( ".dmg" ) < 0 ) cli.addArgument( it.name )
		}
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

	private List<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		list.add( project.file( "${ outputVariantDir.path }/extensions" ) )
		list.add( project.file( "${ outputVariantDir.path }/package" ) )
		list.add( project.file( "${ outputVariantDir.path }/signing" ) )

		return list
	}
}
