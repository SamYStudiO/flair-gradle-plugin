package flair.gradle.tasks

import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.ConfigurationExtensions
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
	//def File outputDir

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

		String moduleName = extensionManager.getFlairProperty( "moduleName" )
		String sPlatform = variant.platform.name.toLowerCase( )
		List<String> excludeResources = extensionManager.getFlairProperty( "excludeResources" , variant ) as List<String>
		String srcRoot = "${ project.projectDir.absolutePath }/${ moduleName }/src/"

		println( sPlatform + "---" + excludeResources )

		project.copy {
			from "${ srcRoot }/main/assets"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/assets"
			}

			from "${ srcRoot }/${ variant.buildType }/assets"

			into "${ project.buildDir }/${ variant.name }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ srcRoot }/main/resources/"

			into "${ project.buildDir }/${ variant.name }/resources/"

			exclude excludeResources
			exclude "**/value*/**"

			includeEmptyDirs = false
		}

		processResourceValues( project.file( "${ srcRoot }/main/resources/" ) )

		for( String flavor : variant.productFlavors )
		{
			project.copy {

				from "${ srcRoot }/${ flavor }/resources/"


				into "${ project.buildDir }/${ variant.name }/resources/"

				exclude excludeResources
				exclude "**/value*/**"

				includeEmptyDirs = false
			}

			processResourceValues( project.file( "${ srcRoot }/${ flavor }/resources/" ) )
		}

		project.copy {
			from "${ srcRoot }/${ variant.buildType }/resources/"

			into "${ project.buildDir }/${ variant.name }/resources/"

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

			into "${ project.buildDir }/${ variant.name }/"
		}

		project.copy {
			from "${ srcRoot }/${ sPlatform }/icons"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/icons"
			}

			from "${ srcRoot }/${ variant.buildType }/icons"

			into "${ project.buildDir }/${ variant.name }/icons"
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
			project.file( "${ project.buildDir }/${ variant.name }/resources/values/" ).mkdirs( )
			project.file( "${ project.buildDir }/${ variant.name }/resources/values/values.xml" ).createNewFile( )
			project.file( "${ project.buildDir }/${ variant.name }/resources/values/values.xml" ).withWriter { writer -> XmlUtil.serialize( output , writer )
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
				project.file( "${ project.buildDir }/${ variant.name }/resources/values-${ qualifiers }/" ).mkdirs( )
				project.file( "${ project.buildDir }/${ variant.name }/resources/values-${ qualifiers }/values-${ qualifiers }.xml" ).createNewFile( )
				project.file( "${ project.buildDir }/${ variant.name }/resources/values-${ qualifiers }/values-${ qualifiers }.xml" ).withWriter { writer -> XmlUtil.serialize( output , writer )
				}
			}
		}
	}

	private String processApp( File app )
	{
		if( !app.exists( ) ) return

		String appContent = app.getText( )
		String sdkVersion = new Sdk( project ).version
		String appId = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "id" , variant )
		String appName = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "appName" , variant )
		String appVersion = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "version" , variant )
		String appFullScreen = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "fullScreen" , variant )
		String appAspectRatio = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "aspectRatio" , variant )
		String appAutoOrient = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "autoOrient" , variant )
		String appDepthAndStencil = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "depthAndStencil" , variant )
		String supportedLocales = getSupportedLocales( )

		appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
								.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
								.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
								.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ appVersion }</versionNumber>" )
								.replaceAll( /<fullScreen>.*<\\/fullScreen>/ , "<fullScreen>${ appFullScreen }</fullScreen>" )
								.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
								.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrient }</autoOrients>" )
								.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
								.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )


		project.file( "${ project.buildDir }/${ variant.name }/app_descriptor.xml" ).write( appContent )
	}

	private String getSupportedLocales()
	{
		FileTree tree = project.fileTree( "${ project.buildDir }/${ variant.name }/resources/" ) {
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

		String defaultLocale = extensionManager.getFlairProperty( ConfigurationExtensions.APP_DESCRIPTOR.name , "defaultSupportedLanguages" , variant )
		if( defaultLocale && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}