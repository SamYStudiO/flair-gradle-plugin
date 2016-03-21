package flair.gradle.structures.fdt

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.structures.IStructure
import flair.gradle.tasks.TaskDefinition
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant.NamingType
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FdtRunDebugConfigurationsStructure implements IStructure
{
	private Project project

	@Override
	public void create( Project project , File source )
	{
		this.project = project

		IExtensionManager extensionManager = project.flair as IExtensionManager

		project.file( ".settings/launch" ).mkdirs( )

		List<String> list = new ArrayList<>( )

		project.file( ".settings/launch" ).listFiles( ).each { list.add( it.name ) }

		String template = project.file( "${ source.path }/fdt/run_debug_template.xml" ).text
		String gradleTemplate = project.file( "${ source.path }/fdt/gradle_launch_template.xml" ).text

		IExtensionManager flair = project.flair as IExtensionManager

		flair.allActivePlatformVariants.each { variant ->

			String name = "flair_" + variant.name

			if( list.indexOf( name + ".launch" ) < 0 )
			{
				String platform = ""

				switch( variant.platform )
				{
					case Platform.IOS:
						platform = "iOS"
						break

					case Platform.ANDROID:
						platform = "Android"
						break

					case Platform.DESKTOP:
						platform = "AirDesktop"
						break
				}

				String app = buildPathFromRoot( project.file( "${ project.buildDir.path }/${ variant.name }/app_descriptor.xml" ).path )
				String mainClass = buildPathFromRoot( project.file( "${ project.buildDir.path }/${ variant.name }/classes/${ ( extensionManager.getFlairProperty( variant , FlairProperty.COMPILER_MAIN_CLASS ) as String ).split( "\\." ).join( "/" ) }.as" ).path )
				String sdk = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_PLATFORM_SDK ) as String ?: ""
				String screenSize = flair.getFlairProperty( variant , FlairProperty.ADL_SCREEN_SIZE ) ?: ""
				String adlArguments = flair.getFlairProperty( variant , FlairProperty.ADL_PARAMETERS ) ?: ""
				String profileName = variant.name
				String sampler = flair.getFlairProperty( variant , FlairProperty.PACKAGE_SAMPLER )
				String gradlePrepareTask = TaskDefinition.PREPARE_PACKAGE.name + variant.getName( NamingType.CAPITALIZE )

				String content = gradleTemplate.replaceAll( "\\{gradlePrepareTask\\}" , gradlePrepareTask )
						.replaceAll( "\\{projectName\\}" , project.name )

				File f = project.file( ".settings/launch/${ gradlePrepareTask }.launch" )
				f.createNewFile( )
				f.write( content )

				List<String> constants = new ArrayList<String>( )


				PluginManager.getCurrentPlatforms( project ).each {

					constants.add( "<listEntry value=\"PLATFORM::${ it.name.toUpperCase( ) }!${ it == variant.platform }\"/>" )
				}

				extensionManager.allActivePlatformProductFlavors.each {

					constants.add( "<listEntry value=\"PRODUCT_FLAVOR::${ it.toUpperCase( ) }!${ variant.productFlavors.indexOf( it ) >= 0 }\"/>" )
				}

				extensionManager.allActivePlatformBuildTypes.each {

					constants.add( "<listEntry value=\"BUILD_TYPE::${ it.toUpperCase( ) }!${ it == variant.buildType }\"/>" )
				}

				content = template.replaceAll( "\\{app\\}" , app )
						.replaceAll( "\\{screenSize\\}" , screenSize )
						.replaceAll( "\\{adlArguments\\}" , adlArguments )
						.replaceAll( "\\{constants\\}" , constants.join( System.lineSeparator( ) ) )
						.replaceAll( "\\{mainClass\\}" , mainClass )
						.replaceAll( "\\{sdk\\}" , sdk )
						.replaceAll( "\\{projectName\\}" , project.name )
						.replaceAll( "\\{sampler\\}" , sampler )
						.replaceAll( "\\{platform\\}" , platform )
						.replaceAll( "\\{profileName\\}" , profileName )
						.replaceAll( "\\{gradlePrepareTask\\}" , gradlePrepareTask )

				f = project.file( ".settings/launch/${ name }.launch" )
				f.createNewFile( )
				f.write( content )
			}
			else
			{
				//need to update compiler constants

				List<String> constants = new ArrayList<String>( )


				PluginManager.getCurrentPlatforms( project ).each {

					constants.add( "<listEntry value=\"PLATFORM::${ it.name.toUpperCase( ) }!${ it == variant.platform }\"/>" )
				}

				extensionManager.allActivePlatformProductFlavors.each {

					constants.add( "<listEntry value=\"PRODUCT_FLAVOR::${ it.toUpperCase( ) }!${ variant.productFlavors.indexOf( it ) >= 0 }\"/>" )
				}

				extensionManager.allActivePlatformBuildTypes.each {

					constants.add( "<listEntry value=\"BUILD_TYPE::${ it.toUpperCase( ) }!${ it == variant.buildType }\"/>" )
				}

				File f = project.file( ".settings/launch/${ name }.launch" )

				String content = f.text.replaceAll( /(<listAttribute key="COMPILER_CONSTANTS">[\s\S]*<\/listAttribute>)/ , "<listAttribute key=\"COMPILER_CONSTANTS\">${ System.lineSeparator( ) }${ constants.join( System.lineSeparator( ) ) }${ System.lineSeparator( ) }</listAttribute>" )

				f.write( content )

				list.remove( list.indexOf( name + ".launch" ) )
			}
		}

		project.file( ".settings/launch" ).listFiles( ).each { if( list.indexOf( it.name ) >= 0 ) it.delete( ) }
	}

	private String buildPathFromRoot( String path )
	{
		path = path.replaceAll( "\\\\" , "/" )
		String rootPath = project.rootDir.path.replaceAll( "\\\\" , "/" ) + "/"

		if( path.startsWith( rootPath ) )
		{
			return path.replace( rootPath , "" )
		}
		else
		{
			int count = 0

			while( !path.startsWith( rootPath ) && rootPath.indexOf( "/" ) > 0 )
			{
				List<String> a = rootPath.split( "/" ).toList( )

				a.pop( )

				rootPath = a.join( "/" )

				count++
			}

			if( path.startsWith( rootPath ) )
			{
				String up = ""

				for( int i = 0; i < count; i++ ) up += "../"

				return up + path.replace( rootPath + "/" , "" )
			}
		}

		return path.replaceAll( "\\\\" , "/" )
	}
}