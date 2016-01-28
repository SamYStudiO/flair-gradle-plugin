package flair.gradle.structures

import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaRunDebugConfigurationsStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		project.file( ".idea/runConfigurations" ).mkdirs( )

		List<String> list = new ArrayList<>( )

		project.file( ".idea/runConfigurations" ).listFiles( ).each { list.add( it.name ) }

		String template = project.file( "${ source.path }/idea/run_debug_template.xml" ).text

		IExtensionManager flair = project.flair as IExtensionManager

		flair.allActivePlatformVariants.each {

			String name = "flair_" + it.getNameWithType( Variant.NamingTypes.UNDERSCORE )

			if( list.indexOf( name + ".xml" ) < 0 )
			{
				String profileName = "flair_" + it.getNameWithType( Variant.NamingTypes.UNDERSCORE )
				String app = it.platform == Platforms.IOS ? "IOS" : it.platform == Platforms.ANDROID ? "Android" : ""
				String transport = flair.getFlairProperty( it , FlairProperties.PACKAGE_CONNECT.name ) ? "Network" : "USB"
				String emulator = it.platform == Platforms.IOS ? "OtherIOSDevice" : it.platform == Platforms.ANDROID ? "OtherAndroidDevice" : ""
				String size = flair.getFlairProperty( it , FlairProperties.EMULATOR_SCREEN_SIZE.name )
				String fullHeight = size.split( ":" )[ 1 ].split( "x" )[ 1 ]
				String fullWidth = size.split( ":" )[ 1 ].split( "x" )[ 0 ]
				String target = "Emulator"
				String moduleName = flair.getFlairProperty( FlairProperties.MODULE_NAME.name )
				String dpi = flair.getFlairProperty( it , FlairProperties.EMULATOR_SCREEN_DPI.name )
				String height = size.split( ":" )[ 0 ].split( "x" )[ 1 ]
				String width = size.split( ":" )[ 0 ].split( "x" )[ 0 ]
				String port = flair.getFlairProperty( it , FlairProperties.PACKAGE_LISTEN.name )

				String content = template.replaceAll( "\\{name\\}" , name )
						.replaceAll( "\\{profileName\\}" , profileName )
						.replaceAll( "\\{app\\}" , app )
						.replaceAll( "\\{transport\\}" , transport )
						.replaceAll( "\\{emulator\\}" , emulator )
						.replaceAll( "\\{fullHeight\\}" , fullHeight )
						.replaceAll( "\\{fullWidth\\}" , fullWidth )
						.replaceAll( "\\{target\\}" , target )
						.replaceAll( "\\{moduleName\\}" , moduleName )
						.replaceAll( "\\{dpi\\}" , dpi )
						.replaceAll( "\\{height\\}" , height )
						.replaceAll( "\\{width\\}" , width )
						.replaceAll( "\\{port\\}" , port )

				println( "----" )
				println( content )

				File f = project.file( ".idea/runConfigurations/${ name }.xml" )
				f.createNewFile( )
				f.write( content )
			}
			else list.removeAt( list.indexOf( name + ".xml" ) )
		}

		project.file( ".idea/runConfigurations" ).listFiles( ).each { if( list.indexOf( it.name ) >= 0 ) it.delete( ) }
	}
}