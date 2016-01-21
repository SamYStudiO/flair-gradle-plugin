package flair.gradle.tasks

import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.Extensions
import flair.gradle.extensions.Properties
import flair.gradle.variants.Variant
import groovy.xml.XmlUtil
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Assemble extends AbstractVariantTask
{
	//@InputDirectory
	//def File inputDir

	//@OutputDirectory
	String outputDir

	//@Input
	//def inputProperty

	public Assemble()
	{
		group = Groups.BUILD.name
		description = ""

		//String moduleName = extensionManager.getFlairProperty( "moduleName" )
		//inputDir = project.file( "${ moduleName }/src/main/resources" )
		//outputDir = project.file( "${ project.buildDir }/${ variant.name }" )
	}

	@TaskAction
	public void assemble( /*IncrementalTaskInputs inputs*/ )
	{
		/*println inputs.incremental ? "CHANGED inputs considered out of date" : "ALL inputs considered out of date"
		if( !inputs.incremental ) project.delete( outputDir.listFiles( ) )

		inputs.outOfDate { change ->
			println "out of date: ${ change.file.name }"
			def targetFile = new File( outputDir , change.file.name )
			targetFile.text = change.file.text.reverse( )
		}

		inputs.removed { change ->
			println "removed: ${ change.file.name }"
			def targetFile = new File( outputDir , change.file.name )
			targetFile.delete( )
		}*/

		String moduleName = extensionManager.getFlairProperty( Properties.MODULE_NAME.name )
		String sPlatform = variant.platform.name.toLowerCase( )
		List<String> excludeResources = extensionManager.getFlairProperty( variant , Properties.EXCLUDE_RESOURCES.name ) as List<String>
		String srcRoot = "${ project.projectDir.absolutePath }/${ moduleName }/src/"
		outputDir = project.file( "${ project.buildDir }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }" )

		project.copy {
			from "${ srcRoot }/main/assets"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/assets"
			}

			from "${ srcRoot }/${ variant.buildType }/assets"



			into "${outputDir }/assets"

			includeEmptyDirs = false
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

		processApp( project.file( "${ srcRoot }/${ variant.platform.name.toLowerCase( ) }/app_descriptor.xml" ) )

		for( String flavor : variant.productFlavors )
		{
			processApp( project.file( "${ srcRoot }/${ flavor }/app_descriptor.xml" ) )
		}
	}

	private String processResourceValues( File resourceDir )
	{
		if( !resourceDir.exists( ) ) return

		Node output = new Node( null , "resources" )

		project.fileTree( "${ resourceDir }/values/" ) {
			include "?*.xml"
		}.each { file ->
			Node xml = new XmlParser( ).parse( file )

			xml.children( ).each { node ->

				output.append( node as Node )
			}
		}

		if( output.children( ).size( ) > 0 )
		{
			project.file( "${ outputDir }/resources/values/" ).mkdirs( )
			project.file( "${ outputDir }/resources/values/values.xml" ).createNewFile( )
			project.file( "${ outputDir }/resources/values/values.xml" ).withWriter { writer -> XmlUtil.serialize( output , writer )
			}
		}

		output = new Node( null , "resources" )

		project.fileTree( "${ resourceDir }" ) {
			include "values-?*/?*.xml"
		}.each { file ->

			String qualifiers = file.parentFile.name.replace( "values-" , "" )

			Node xml = new XmlParser( ).parse( file )

			xml.children( ).each { node ->

				output.append( node as Node )
			}

			if( output.children( ).size( ) > 0 )
			{
				project.file( "${ outputDir }/resources/values-${ qualifiers }/" ).mkdirs( )
				project.file( "${ outputDir }/resources/values-${ qualifiers }/values-${ qualifiers }.xml" ).createNewFile( )
				project.file( "${ outputDir }/resources/values-${ qualifiers }/values-${ qualifiers }.xml" ).withWriter { writer -> XmlUtil.serialize( output , writer )
				}
			}
		}
	}

	private String processApp( File app )
	{
		if( !app.exists( ) ) return

		String appContent = app.getText( )
		String sdkVersion = new Sdk( project ).version
		String appId = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_ID.name ) + extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_ID_SUFFIX.name )
		String appName = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_NAME.name ) + extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_NAME_SUFFIX.name )
		String appFileName = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_FILE_NAME.name )
		String appSWF = variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + ".swf"
		String appVersion = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_VERSION.name )
		String appFullScreen = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_FULL_SCREEN.name )
		String appAspectRatio = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_ASPECT_RATIO.name )
		String appAutoOrient = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_AUTO_ORIENT.name )
		String appDepthAndStencil = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_DEPTH_AND_STENCIL.name )
		String supportedLocales = getSupportedLocales( )

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

		String defaultLocale = extensionManager.getFlairProperty( Extensions.APP_DESCRIPTOR.name , variant , Properties.APP_DEFAULT_SUPPORTED_LANGUAGES.name )
		if( defaultLocale && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}