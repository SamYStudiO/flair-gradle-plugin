package flair.gradle.structures.idea

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.structures.IStructure
import flair.gradle.tasks.TaskDefinition
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant.NamingType
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IdeaRunDebugConfigurationsStructure implements IStructure
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

			String name = "flair_" + it.name

			if( list.indexOf( name + ".xml" ) < 0 )
			{
				String profileName = "flair_" + it.name
				String app = it.platform == Platform.IOS ? "IOS" : it.platform == Platform.ANDROID ? "Android" : ""
				String transport = flair.getFlairProperty( it , FlairProperty.PACKAGE_CONNECT ) ? "Network" : "USB"
				String emulator = it.platform == Platform.IOS ? "OtherIOSDevice" : it.platform == Platform.ANDROID ? "OtherAndroidDevice" : ""
				String size = flair.getFlairProperty( it , FlairProperty.ADL_SCREEN_SIZE )
				String fullHeight = size.split( ":" )[ 1 ].split( "x" )[ 1 ]
				String fullWidth = size.split( ":" )[ 1 ].split( "x" )[ 0 ]
				String target = "Emulator"
				String moduleName = flair.getFlairProperty( FlairProperty.MODULE_NAME )
				String dpi = flair.getFlairProperty( it , FlairProperty.ADL_SCREEN_DPI )
				String height = size.split( ":" )[ 0 ].split( "x" )[ 1 ]
				String width = size.split( ":" )[ 0 ].split( "x" )[ 0 ]
				String port = flair.getFlairProperty( it , FlairProperty.PACKAGE_LISTEN ) ?: "7936"
				String gradlePrepareTask = TaskDefinition.PREPARE_PACKAGE.name + it.getName( NamingType.CAPITALIZE )

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
						.replaceAll( "\\{gradlePrepareTask\\}" , gradlePrepareTask )

				File f = project.file( ".idea/runConfigurations/${ name }.xml" )
				f.createNewFile( )
				f.write( content )
			}
			else list.remove( list.indexOf( name + ".xml" ) )
		}

		project.file( ".idea/runConfigurations" ).listFiles( ).each { if( list.indexOf( it.name ) >= 0 ) it.delete( ) }
	}
}