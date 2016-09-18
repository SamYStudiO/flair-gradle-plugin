package flair.gradle.structures.fdt

import flair.gradle.cli.Adl
import flair.gradle.cli.ICli
import flair.gradle.dependencies.Config
import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.structures.IStructure
import flair.gradle.tasks.TaskDefinition
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant.NamingType
import org.gradle.api.Project
import org.gradle.api.file.FileTree

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
				String mainClassName = ( extensionManager.getFlairProperty( variant , FlairProperty.COMPILER_MAIN_CLASS ) as String ).split( "\\." ).last( ) + ".as"
				String mainClass

				project.configurations.findAll { it.name.toLowerCase( ).contains( Config.SOURCE.name ) }.each {

					it.files.each { file ->

						FileTree tree = project.fileTree( file )

						tree.each { classFile ->

							if( classFile.name == mainClassName ) mainClass = buildPathFromRoot( classFile.path )
						}
					}
				}

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

				String pubId = extensionManager.getFlairProperty( variant , FlairProperty.ADL_PUB_ID )
				boolean noDebug = extensionManager.getFlairProperty( variant , FlairProperty.ADL_NO_DEBUG )
				boolean atLogin = extensionManager.getFlairProperty( variant , FlairProperty.ADL_AT_LOGIN )
				List<String> parameters = extensionManager.getFlairProperty( variant , FlairProperty.ADL_PARAMETERS ) as List<String>

				ICli cli = new Adl( )
				cli.addArgument( "-profile" )
				cli.addArgument( variant.platform == Platform.DESKTOP ? "extendedDesktop" : "mobileDevice" )

				if( pubId ) cli.addArgument( "-pubid ${ pubId }" )
				if( noDebug ) cli.addArgument( "-noDebug" )
				if( atLogin ) cli.addArgument( "-atlogin" )

				if( variant.platform != Platform.DESKTOP )
				{
					cli.addArgument( "-screensize" )
					cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperty.ADL_SCREEN_SIZE ).toString( ) )
					cli.addArgument( "-XscreenDPI" )
					cli.addArgument( extensionManager.getFlairProperty( variant , FlairProperty.ADL_SCREEN_DPI ).toString( ) )
					cli.addArgument( "-XversionPlatform" )
					cli.addArgument( variant.platform == Platform.IOS ? "IOS" : "AND" )
				}

				cli.addArgument( "-extdir" )
				cli.addArgument( project.file( "${ project.buildDir.path }/${ variant.name }/extracted_extensions" ).path )

				cli.addArgument( "${ project.buildDir.path }/${ variant.name }/package/app_descriptor.xml" )
				cli.addArgument( project.file( "${ project.buildDir.path }/${ variant.name }/package" ).path )

				if( parameters.size( ) )
				{
					cli.addArgument( "--" )
					cli.addArguments( parameters )
				}

				content = template.replaceAll( "\\{app\\}" , app )
						.replaceAll( "\\{screenSize\\}" , screenSize )
						.replaceAll( "\\{adlArguments\\}" , adlArguments )
						.replaceAll( "\\{adl\\}" , cli.arguments.join( " " ).replaceAll( "\\\\" , "/" ) )
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

				String content = f.text
				content = content.replaceAll( /<listAttribute key="COMPILER_CONSTANTS">[\s\S]*<\/listAttribute>[\s]*<stringAttribute/ , "<listAttribute key=\"COMPILER_CONSTANTS\">${ System.lineSeparator( ) }${ constants.join( System.lineSeparator( ) ) }${ System.lineSeparator( ) }</listAttribute>${ System.lineSeparator( ) }<stringAttribute" )
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