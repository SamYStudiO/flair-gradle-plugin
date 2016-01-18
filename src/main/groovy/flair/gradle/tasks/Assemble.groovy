package flair.gradle.tasks

import flair.gradle.extensions.ConfigurationExtension
import flair.gradle.extensions.PropertyManager
import flair.gradle.utils.AIRSDKManager
import groovy.xml.XmlUtil
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Assemble extends AbstractVariantTask
{
	@InputDirectory
	def File inputDir

	@OutputDirectory
	def File outputDir

	//@Input
	//def inputProperty

	public Assemble()
	{
		group = Group.BUILD.name
		description = ""

		String moduleName = PropertyManager.getProperty( project , "moduleName" )
		inputDir = project.file( "${ project.projectDir.absolutePath }/${ moduleName }/src/main/resources" )
		outputDir = project.buildDir
	}

	@TaskAction
	public void assemble( IncrementalTaskInputs inputs )
	{
		println inputs.incremental ? "CHANGED inputs considered out of date" : "ALL inputs considered out of date"
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
		}

		String moduleName = PropertyManager.getProperty( project , "moduleName" )
		String sPlatform = variant.platform.name.toLowerCase( )
		List<String> excludeResources = PropertyManager.getProperty( project , "excludeResources" ) as List<String>
		String srcRoot = "${ project.projectDir.absolutePath }/${ moduleName }/src/"

		project.copy {
			from "${ srcRoot }/main/assets"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/assets"
			}

			from "${ srcRoot }/${ variant.buildType }/assets"

			into "${ project.buildDir }/assets"

			includeEmptyDirs = false
		}

		project.copy {
			from "${ srcRoot }/main/resources/"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/resources/"
			}

			from "${ srcRoot }/${ variant.buildType }/resources/"

			into "${ project.buildDir }/resources/"

			exclude excludeResources
			exclude "**/value*/**"

			includeEmptyDirs = false
		}

		processResourceValues( srcRoot )

		project.copy {
			from "${ srcRoot }/${ sPlatform }/splashs"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/splashs"
			}

			from "${ srcRoot }/${ variant.buildType }/splashs"

			into "${ project.buildDir }/"
		}

		project.copy {
			from "${ srcRoot }/${ sPlatform }/icons"

			variant.productFlavors.each {
				from "${ srcRoot }/${ it }/icons"
			}

			from "${ srcRoot }/${ variant.buildType }/icons"

			into "${ project.buildDir }/icons"
		}

		processApp( project.file( "${ srcRoot }/${ variant.platform.name.toLowerCase( ) }/app_descriptor.xml" ) )

		variant.productFlavors.each {
			processApp( project.file( "${ srcRoot }/${ it }/app_descriptor.xml" ) )
		}
	}

	private String processResourceValues( String rootPath )
	{
		Node output = new Node( null , "resources" )

		project.fileTree( "${ rootPath }/main/resources/values/" ) {
			include "?*.xml"
		}.each { file ->
			Node xml = new XmlParser( ).parse( file )

			xml.children( ).each { node ->

				output.append( node as Node )
			}
		}

		if( output.children( ).size( ) > 0 )
		{
			project.file( "${ project.buildDir }/resources/values/" ).mkdirs( )
			project.file( "${ project.buildDir }/resources/values/values.xml" ).createNewFile( )
			project.file( "${ project.buildDir }/resources/values/values.xml" ).withWriter { writer -> XmlUtil.serialize( output , writer )
			}
		}

		output = new Node( null , "resources" )

		project.fileTree( "${ rootPath }/main/resources/" ) {
			include "values-?*/?*.xml"
		}.each { file ->

			String qualifiers = file.parentFile.name.replace( "values-" , "" )

			Node xml = new XmlParser( ).parse( file )

			xml.children( ).each { node ->

				output.append( node as Node )
			}

			if( output.children( ).size( ) > 0 )
			{
				project.file( "${ project.buildDir }/resources/values-${ qualifiers }/" ).mkdirs( )
				project.file( "${ project.buildDir }/resources/values-${ qualifiers }/values-${ qualifiers }.xml" ).createNewFile( )
				project.file( "${ project.buildDir }/resources/values-${ qualifiers }/values-${ qualifiers }.xml" ).withWriter { writer -> XmlUtil.serialize( output , writer )
				}
			}
		}
	}

	private String processApp( File app )
	{
		String appContent = app.getText( )
		String sdkVersion = AIRSDKManager.getVersion( project )
		String appId = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "id" , variant.platform , productFlavor , variant.buildType )
		String appName = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "appName" , variant.platform , productFlavor , variant.buildType )
		String appVersion = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "version" , variant.platform , productFlavor , variant.buildType )
		String appFullScreen = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "fullScreen" , variant.platform , productFlavor , variant.buildType )
		String appAspectRatio = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "aspectRatio" , variant.platform , productFlavor , variant.buildType )
		String appAutoOrient = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "autoOrient" , variant.platform , productFlavor , variant.buildType )
		String appDepthAndStencil = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "depthAndStencil" , variant.platform , productFlavor , variant.buildType )
		String supportedLocales = getSupportedLocales( )
		boolean desktop = appContent.indexOf( "<android>" ) < 0 && appContent.indexOf( "<iPhone>" ) < 0

		if( desktop )
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
					.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ appVersion }</versionNumber>" )
					.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}
		else
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
					.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
					.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ appVersion }</versionNumber>" )
					.replaceAll( /<fullScreen>.*<\\/fullScreen>/ , "<fullScreen>${ appFullScreen }</fullScreen>" )
					.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
					.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrient }</autoOrients>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}

		project.file( "${ project.buildDir }/app_descriptor.xml" ).write( appContent )
	}

	private String getSupportedLocales()
	{
		FileTree tree = project.fileTree( "${ project.buildDir }/resources/" ) {
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

		String defaultLocale = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "defaultSupportedLanguages" , variant.platform , productFlavor , variant.buildType )
		if( defaultLocale && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}