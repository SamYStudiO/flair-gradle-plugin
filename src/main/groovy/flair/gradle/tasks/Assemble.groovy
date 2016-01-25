package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.Png2Atf
import flair.gradle.dependencies.Configurations
import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Variant
import groovy.xml.XmlUtil
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Assemble extends AbstractVariantTask
{
	protected String outputDir

	public Assemble()
	{
		group = Groups.BUILD.name
		description = ""
	}

	@TaskAction
	public void assemble()
	{
		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name )
		String sPlatform = variant.platform.name.toLowerCase( )
		List<String> excludeResources = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_EXCLUDE_RESOURCES.name ) as List<String>
		String srcRoot = "${ project.projectDir.absolutePath }/${ moduleName }/src/"
		outputDir = project.file( "${ project.buildDir }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }" )

		project.copy {
			from "${ srcRoot }/main/assets"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/assets"
			}

			from "${ srcRoot }/${ variant.buildType }/assets"

			into "${ outputDir }/assets"

			includeEmptyDirs = false
		}

		Collection<File> packagedFiles = project.configurations.getByName( Configurations.PACKAGE.name ).files

		packagedFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.PACKAGE.name.capitalize( ) ) )
		variant.productFlavors.each {

			packagedFiles.addAll( project.configurations.getByName( it + Configurations.PACKAGE.name.capitalize( ) ) )
		}

		packagedFiles.each { file ->

			project.copy {
				from file.path
				into "${ outputDir }/${ file.path.split( "/" ).last( ) }"
			}
		}

		project.copy {
			from "${ srcRoot }/main/resources/"

			into "${ outputDir }/resources/"

			exclude excludeResources
			exclude "**/value*/**"

			includeEmptyDirs = false
		}

		processResourceValues( project.file( "${ srcRoot }/main/resources/" ) )

		for( String flavor : variant.productFlavors )
		{
			project.copy {

				from "${ srcRoot }/${ flavor }/resources/"


				into "${ outputDir }/resources/"

				exclude excludeResources
				exclude "**/value*/**"

				includeEmptyDirs = false
			}

			processResourceValues( project.file( "${ srcRoot }/${ flavor }/resources/" ) )
		}

		project.copy {
			from "${ srcRoot }/${ variant.buildType }/resources/"

			into "${ outputDir }/resources/"

			exclude excludeResources
			exclude "**/value*/**"

			includeEmptyDirs = false
		}

		processResourceValues( project.file( "${ srcRoot }/${ variant.buildType }/resources/" ) )

		if( extensionManager.getFlairProperty( variant , FlairProperties.GENERATE_ATF_TEXTURES_FROM_DRAWABLES.name ) ) generateAtfTextures( )

		project.copy {
			from "${ srcRoot }/${ sPlatform }/splashs"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/splashs"
			}

			from "${ srcRoot }/${ variant.buildType }/splashs"

			into "${ outputDir }/"
		}

		project.copy {
			from "${ srcRoot }/${ sPlatform }/icons"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/icons"
			}

			from "${ srcRoot }/${ variant.buildType }/icons"

			into "${ outputDir }/icons"
		}

		List<File> extensions = new ArrayList<File>( )

		project.configurations.getByName( Configurations.NATIVE_COMPILE.name ).files.each {

			extensions.add( it )
		}

		project.configurations.getByName( variant.platform.name + Configurations.NATIVE_COMPILE.name.capitalize( ) ).files.each {

			extensions.add( it )
		}

		variant.productFlavors.each {

			project.configurations.getByName( it + Configurations.NATIVE_COMPILE.name.capitalize( ) ).files.each {

				extensions.add( it )
			}
		}

		project.configurations.getByName( variant.buildType + Configurations.NATIVE_COMPILE.name.capitalize( ) ).files.each {

			extensions.add( it )
		}

		extensions.each { file ->
			project.copy {

				from file
				into "${ outputDir }/extensions"
			}
		}

		File extensionsDir = project.file( "${ outputDir }/extensions" )

		if( extensionsDir.exists( ) )
		{
			extensionsDir.listFiles( ).each { file ->
				project.copy {

					from project.zipTree( file )
					into "${ outputDir }/extracted_extensions/${ file.name }"
				}
			}
		}

		processApp( project.file( "${ srcRoot }/${ variant.platform.name.toLowerCase( ) }/app_descriptor.xml" ) )

		for( String flavor : variant.productFlavors )
		{
			processApp( project.file( "${ srcRoot }/${ flavor }/app_descriptor.xml" ) )
		}
	}

	private generateAtfTextures()
	{
		ICli png2atf = new Png2Atf( )

		FileTree tree = project.fileTree( "${ outputDir }/resources/" ) { include "**/*.png" }

		tree.each {

			String input = it.absolutePath
			String output = input.replaceAll( "\\.png" , "\\.atf" )

			png2atf.clearArguments( )
			png2atf.addArgument( "-i" )
			png2atf.addArgument( input )
			png2atf.addArgument( "-o" )
			png2atf.addArgument( output )

			png2atf.execute( project )
		}
	}

	private String processResourceValues( File resourceDir )
	{
		if( !resourceDir.exists( ) ) return

		project.fileTree( "${ resourceDir }" ) { include "values*/?*.xml" }.each { file ->

			String qualifiers = file.parentFile.name.replace( "values" , "" )

			project.file( "${ outputDir }/resources/values${ qualifiers }/" ).mkdirs( )
			File outputFile = project.file( "${ outputDir }/resources/values${ qualifiers }/values${ qualifiers }.xml" )

			if( !outputFile.exists( ) ) outputFile.createNewFile( )

			Node output

			if( outputFile.text.isEmpty( ) ) output = new Node( null , "resources" ) else output = new XmlParser( ).parse( outputFile )

			Node xml = new XmlParser( ).parse( file )

			xml.children( ).each { Node node ->

				Node old = output.children( ).find { it.name( ) == node.name( ) && it.'@name' == node.'@name' } as Node

				if( old ) output.remove( old )

				output.append( node )
			}

			if( output.children( ).size( ) > 0 )
			{
				outputFile.withWriter { writer -> XmlUtil.serialize( output , writer ) }
			}
		}
	}

	private String processApp( File app )
	{
		if( !app.exists( ) ) return

		String appContent = app.text
		String sdkVersion = new Sdk( project ).version
		String appId = extensionManager.getFlairProperty( variant , FlairProperties.APP_ID.name ) + extensionManager.getFlairProperty( variant , FlairProperties.APP_ID_SUFFIX.name )
		String appName = extensionManager.getFlairProperty( variant , FlairProperties.APP_NAME.name ) + extensionManager.getFlairProperty( variant , FlairProperties.APP_NAME_SUFFIX.name )
		String appFileName = extensionManager.getFlairProperty( variant , FlairProperties.APP_FILE_NAME.name )
		String appSWF = variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + ".swf"
		String appVersion = extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION.name )
		String appFullScreen = extensionManager.getFlairProperty( variant , FlairProperties.APP_FULL_SCREEN.name )
		String appAspectRatio = extensionManager.getFlairProperty( variant , FlairProperties.APP_ASPECT_RATIO.name )
		String appAutoOrient = extensionManager.getFlairProperty( variant , FlairProperties.APP_AUTO_ORIENT.name )
		String appDepthAndStencil = extensionManager.getFlairProperty( variant , FlairProperties.APP_DEPTH_AND_STENCIL.name )
		String supportedLocales = getSupportedLocales( )


		FileTree extractedExtensions = project.fileTree( "${ outputDir }/extracted_extensions/" )
		String extensionNodes = System.lineSeparator( )
		boolean hasExtensions = false

		extractedExtensions.each {

			if( it.name == "extension.xml" )
			{
				extensionNodes += "\t\t<extensionID>${ new XmlParser( ).parse( it ).id[ 0 ].text( ) }</extensionID>" + System.lineSeparator( )
				hasExtensions = true
			}
		}

		if( hasExtensions ) extensionNodes += "\t"

		appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
				.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
				.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
				.replaceAll( /<filename>.*<\\/filename>/ , "<filename>${ appFileName }</filename>" )
				.replaceAll( /<content>.*<\\/content>/ , "<content>${ appSWF }</content>" )
				.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ appVersion }</versionNumber>" )
				.replaceAll( /<fullScreen>.*<\\/fullScreen>/ , "<fullScreen>${ appFullScreen }</fullScreen>" )
				.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
				.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrient }</autoOrients>" )
				.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
				.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
				.replaceAll( /<extensions>.*<\\/extensions>/ , "<extensions>${ extensionNodes }</extensions>" )


		project.file( "${ outputDir }/app_descriptor.xml" ).write( appContent )
	}

	private String getSupportedLocales()
	{
		FileTree tree = project.fileTree( "${ outputDir }/resources/" ) {
			include "**/*.xml"
		}

		String locales = "en de cs es fr it ja ko nl pl pt ru sv tr zh"
		String supportedLocales = ""

		tree.each { file ->

			String parent = file.getParentFile( ).getName( )

			if( parent.indexOf( "values-" ) >= 0 )
			{
				String locale = parent.find( /-([a-z]{2,3})(?:-|$)/ )

				if( locale && locale.indexOf( "-" ) >= 0 ) locale = locale.replaceAll( /-/ , "" );

				if( locales.indexOf( locale ) >= 0 && supportedLocales.indexOf( locale ) < 0 ) supportedLocales = supportedLocales.concat( locale + " " )
			}
		}

		String defaultLocale = extensionManager.getFlairProperty( variant , FlairProperties.APP_DEFAULT_SUPPORTED_LANGUAGES.name )
		if( defaultLocale && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}