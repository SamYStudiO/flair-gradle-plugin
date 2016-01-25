package flair.gradle.tasks

import flair.gradle.cli.Adt
import flair.gradle.cli.ICli
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Package extends AbstractVariantTask
{
	public ICli cli = new Adt( )

	public String input

	public String output

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

		addTarget( )

		cli.addArgument( "-arch" )
		cli.addArgument( "x86" )

		cli.addArgument( "-storetype" )
		cli.addArgument( "pkcs12" )
		cli.addArgument( "-keystore" )
		cli.addArgument( project.file( "app/src/android/signing/certificate.p12" ).path )
		cli.addArgument( "-storepass" )
		cli.addArgument( "0000" )


		addOutput( )

		//cli.addArgument( "-e" )
		cli.addArgument( project.file( "${ input }/app_descriptor.xml" ).path )
		//cli.addArgument( "" )
		//cli.addArgument( "-C" )
		//cli.addArgument( project.file( "${ input }" ).path )
		//cli.addArgument( "${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" )
		//cli.addArgument( "." )
		//cli.addArgument( "-e" )
		//cli.addArgument( project.file( "${ input }/app_descriptor.xml" ).path )

		//String swf = project.file( "${ input }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" ).path

		cli.addArgument( "-C" )
		cli.addArgument( project.file( input ).path )
		cli.addArgument( "icons" )
		cli.addArgument( "resources" )
		cli.addArgument( "${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" )

		project.file( input ).listFiles( ).each {

			if( it.name.indexOf( ".png" ) > 0 ) cli.addArgument( it.name )
		}

		cli.addArgument( "-extdir" )
		cli.addArgument( project.file( "${ input }/extensions" ).path )

		//cli.addArgument( "icons" )
		//cli.addArgument( "" )
		//cli.addArgument( "-e" )
		//cli.addArgument( swf )
		//cli.addArgument( "app_descriptor.xml" )
		//cli.addArgument( "${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" )
		//cli.addArgument( "" )
		//cli.addArgument( "" )

		cli.execute( project )
	}

	private addTarget()
	{
		cli.addArgument( "-target" )

		switch( variant.platform )
		{
			case Platforms.IOS:

				break

			case Platforms.ANDROID:

				cli.addArgument( "apk-debug" )
				//cli.addArgument( "-listen" )
				break

			case Platforms.DESKTOP:
				cli.addArgument( "native" )
				break
		}
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
				extension = "air"
				break
		}

		cli.addArgument( project.file( "${ output }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.${ extension }" ).path )
	}
}
