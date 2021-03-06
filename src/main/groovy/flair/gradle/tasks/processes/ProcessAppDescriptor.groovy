package flair.gradle.tasks.processes

import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperty
import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

import java.nio.charset.Charset

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAppDescriptor extends VariantTask
{
	@InputFiles
	Set<File> inputFiles

	@OutputFile
	File outputFile

	@Input
	String sdkVersion

	@Input
	String appId

	@Input
	String appName

	@Input
	String appFileName

	String appSWF

	@Input
	String appVersionName

	@Input
	String appVersionBuild

	@Input
	String appFullScreen

	@Input
	String appAspectRatio

	@Input
	String appAutoOrients

	@Input
	String appDepthAndStencil

	@Input
	String supportedLocales

	@Override
	void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputFile = project.file( "${ outputVariantDir.path }/package/app_descriptor.xml" )

		sdkVersion = new Sdk( project , variant.platform ).version
		appId = extensionManager.getFlairProperty( variant , FlairProperty.APP_ID ) + extensionManager.getFlairProperty( variant , FlairProperty.APP_ID_SUFFIX )
		appName = extensionManager.getFlairProperty( variant , FlairProperty.APP_NAME ) + extensionManager.getFlairProperty( variant , FlairProperty.APP_NAME_SUFFIX )
		appFileName = extensionManager.getFlairProperty( variant , FlairProperty.APP_FILE_NAME )
		appSWF = variant.name + ".swf"
		appVersionName = extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION_LABEL )
		appVersionBuild = extensionManager.getFlairProperty( variant , FlairProperty.APP_VERSION_NUMBER )
		appFullScreen = extensionManager.getFlairProperty( variant , FlairProperty.APP_FULL_SCREEN )
		appAspectRatio = extensionManager.getFlairProperty( variant , FlairProperty.APP_ASPECT_RATIO )
		appAutoOrients = extensionManager.getFlairProperty( variant , FlairProperty.APP_AUTO_ORIENTS )
		appDepthAndStencil = extensionManager.getFlairProperty( variant , FlairProperty.APP_DEPTH_AND_STENCIL )
		supportedLocales = getSupportedLocales( )

		description = "Processes app descriptor templates into ${ variant.name } ${ project.buildDir.name } directory"
	}

	ProcessAppDescriptor()
	{
		group = TaskGroup.DEFAULT.name
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void processAppDescriptor()
	{
		for( File file : inputFiles )
		{
			if( file.exists( ) && file.name.indexOf( ".xml" ) > 0 )
			{
				internalProcessAppDescriptor( file )
				break
			}
		}
	}

	private void internalProcessAppDescriptor( File app )
	{
		String appContent = app.text

		FileTree extractedExtensions = project.fileTree( "${ outputVariantDir.path }/extracted_extensions/" )
		String extensionNodes = System.lineSeparator( )
		boolean hasExtensions = false

		extractedExtensions.each {

			if( it.name == "extension.xml" )
			{
				String id = new XmlParser( ).parse( it ).id[ 0 ].text( )

				if( extensionNodes.indexOf( id ) < 0 )
				{
					extensionNodes += "\t\t<extensionID>${ id }</extensionID>" + System.lineSeparator( )
					hasExtensions = true
				}
			}
		}

		if( hasExtensions ) extensionNodes += "\t"

		String iconNodes = System.lineSeparator( )

		project.file( "${ outputVariantDir.path }/package/icons" ).listFiles( ).each {

			String size = it.name.replace( "icon" , "" ).split( "\\." )[ 0 ]
			iconNodes += "\t\t<image${ size }>icons/${ it.name }</image${ size }>" + System.lineSeparator( )
		}

		iconNodes += "\t"

		appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
				.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
				.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
				.replaceAll( /<filename>.*<\\/filename>/ , "<filename>${ appFileName }</filename>" )
				.replaceAll( /<content>.*<\\/content>/ , "<content>${ appSWF }</content>" )
				.replaceAll( /<versionLabel>.*<\\/versionLabel>/ , "<versionLabel>${ appVersionName }</versionLabel>" )
				.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ appVersionBuild }</versionNumber>" )
				.replaceAll( /<fullScreen>.*<\\/fullScreen>/ , "<fullScreen>${ appFullScreen }</fullScreen>" )
				.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
				.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrients }</autoOrients>" )
				.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
				.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
				.replaceAll( /<extensions>.*<\\/extensions>/ , "<extensions>${ extensionNodes }</extensions>" )
				.replaceAll( /<icon>.*<\\/icon>/ , "<icon>${ iconNodes }</icon>" )

		project.file( "${ outputVariantDir.path }/package/" ).mkdirs( )
		outputFile.createNewFile( )
		outputFile.withOutputStream { writer -> writer.write( appContent.getBytes( Charset.forName( "UTF-8" ) ) ) }
	}

	private String getSupportedLocales()
	{
		FileTree tree = project.fileTree( "${ outputVariantDir.path }/package/res" ) {
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

		String defaultLocale = extensionManager.getFlairProperty( variant , FlairProperty.APP_DEFAULT_SUPPORTED_LANGUAGES )
		if( defaultLocale && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directories.each { list.add( project.file( "${ moduleDir }/src/${ it }/app_descriptor.xml" ) ) }

		list.add( project.file( "${ outputVariantDir }/extracted_extensions" ) )
		list.add( project.file( "${ outputVariantDir }/package/icons" ) )

		return list.reverse( )
	}
}
