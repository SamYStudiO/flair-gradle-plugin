package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.extensions.FlairProperty
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Package extends VariantTask
{
	protected ICli cli = new Adt( )

	@InputFiles
	Set<File> inputFiles

	@OutputFile
	File outputFile

	@Input
	String connect

	@Input
	String listen

	@Input
	boolean sampler

	@Input
	boolean hideAneLibSymbols

	@Input
	boolean x86

	@Input
	String target

	@Input
	String platformSdk

	@Input
	boolean debug

	@Input
	String versionName

	@Input
	String versionBuild

	@Input
	String alias

	@Input
	String provider

	@Input
	String storeType

	@Input
	String storePass

	@Input
	String keyStore

	@Input
	String keyPass

	@Input
	String tsa

	@Input
	String provisioning

	@Override
	void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )

		connect = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_CONNECT ) ?: "null"
		listen = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_LISTEN ) ?: "null"
		sampler = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_SAMPLER )
		hideAneLibSymbols = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_HIDE_ANE_LIB_SYMBOLS )
		x86 = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_X86 )
		target = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_TARGET ) ?: "null"
		platformSdk = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_PLATFORM_SDK ) ?: "null"
		versionName = extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION_LABEL )
		versionBuild = extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION_NUMBER )
		debug = extensionManager.getFlairProperty( variant , FlairProperty.DEBUG )

		alias = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_ALIAS ) ?: "null"
		provider = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_PROVIDER_NAME ) ?: "null"
		storeType = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_STORE_TYPE ) ?: "null"
		storePass = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_STORE_PASS ) ?: "null"
		keyStore = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_KEY_STORE ) ?: "null"
		keyPass = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_KEY_PASS ) ?: "null"
		tsa = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_TSA ) ?: "null"
		provisioning = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_PROVISIONING_PROFILE ) ?: "null"

		outputFile = project.file( "${ project.buildDir.path }/${ extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_FILE_NAME ) }.${ variant.platform.extension }" )

		description = "Packages ${ variant.name } into ${ project.buildDir.name } directory"
	}

	Package()
	{
		group = TaskGroup.PACKAGE.name
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void packaging()
	{
		cli.clearArguments( )

		cli.addArgument( "-package" )

		if( variant.platform == Platform.DESKTOP ) addSigning( )
		addTarget( )
		if( variant.platform != Platform.DESKTOP )
		{
			if( connect != "null" ) addConnect( ) else if( listen != "null" ) addListen( )
		}
		if( variant.platform == Platform.IOS )
		{
			if( sampler ) addSampler( )
			if( hideAneLibSymbols ) addHideAneLibSymbols( )
		}
		if( variant.platform == Platform.ANDROID ) addArchitecture( )
		if( variant.platform != Platform.DESKTOP ) addSigning( )
		addOutput( )
		addFilesAndDirectories( )
		addExtensionNatives( )

		cli.execute( project , variant.platform )
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
		cli.addArgument( project.file( "${ outputVariantDir.path }/package/app_descriptor.xml" ).path )
		if( platformSdk != "null" ) cli.addArgument( "-platformsdk ${ platformSdk }" )
		cli.addArgument( "-C" )
		cli.addArgument( project.file( "${ outputVariantDir.path }/package" ).path )

		project.file( "${ outputVariantDir.path }/package" ).listFiles( ).each {
			if( it.name != "app_descriptor.xml" && it.name.indexOf( ".apk" ) < 0 && it.name.indexOf( ".ipa" ) < 0 && it.name.indexOf( ".exe" ) < 0 && it.name.indexOf( ".dmg" ) < 0 && it.name.indexOf( ".tmp" ) < 0 && it.name != "tmp" ) cli.addArgument( it.name )
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

	private List<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		list.add( project.file( "${ outputVariantDir.path }/extensions" ) )
		list.add( project.file( "${ outputVariantDir.path }/package" ) )
		list.add( project.file( "${ outputVariantDir.path }/signing" ) )

		return list
	}
}
