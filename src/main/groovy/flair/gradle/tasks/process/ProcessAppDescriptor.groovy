package flair.gradle.tasks.process

import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperties
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAppDescriptor extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputDirs

	@OutputDirectory
	def File outputDir

	@Input
	def String sdkVersion

	@Input
	def String appId

	@Input
	def String appName

	@Input
	def String appFileName

	def String appSWF

	@Input
	def String appVersion

	@Input
	def String appFullScreen

	@Input
	def String appAspectRatio

	@Input
	def String appAutoOrient

	@Input
	def String appDepthAndStencil

	@Input
	def String supportedLocales

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputDirs = getInputFiles( )
		outputDir = outputVariantDir

		sdkVersion = new Sdk( project ).version
		appId = extensionManager.getFlairProperty( variant , FlairProperties.APP_ID.name ) + extensionManager.getFlairProperty( variant , FlairProperties.APP_ID_SUFFIX.name )
		appName = extensionManager.getFlairProperty( variant , FlairProperties.APP_NAME.name ) + extensionManager.getFlairProperty( variant , FlairProperties.APP_NAME_SUFFIX.name )
		appFileName = extensionManager.getFlairProperty( variant , FlairProperties.APP_FILE_NAME.name )
		appSWF = variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + ".swf"
		appVersion = extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION.name )
		appFullScreen = extensionManager.getFlairProperty( variant , FlairProperties.APP_FULL_SCREEN.name )
		appAspectRatio = extensionManager.getFlairProperty( variant , FlairProperties.APP_ASPECT_RATIO.name )
		appAutoOrient = extensionManager.getFlairProperty( variant , FlairProperties.APP_AUTO_ORIENT.name )
		appDepthAndStencil = extensionManager.getFlairProperty( variant , FlairProperties.APP_DEPTH_AND_STENCIL.name )
		supportedLocales = getSupportedLocales( )
	}

	public ProcessAppDescriptor()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processAppDescriptor()
	{
		for( File file : getInputFiles( ) )
			if( file.exists( ) ) internalProcessAppDescriptor( file )
	}

	protected String internalProcessAppDescriptor( File app )
	{
		String appContent = app.text

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


		project.file( "${ outputDir }/package/" ).mkdirs( )
		File file = project.file( "${ outputDir }/package/app_descriptor.xml" )
		file.createNewFile( )
		file.write( appContent )
	}

	private String getSupportedLocales()
	{
		FileTree tree = project.fileTree( "${ outputDir }/package/resources/" ) {
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

	private List<File> getInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		list.add( project.file( "${ moduleDir }/src/${ variant.platform.name }/app_descriptor.xml" ) )
		variant.productFlavors.each { list.add( project.file( "${ moduleDir }/src/${ it }/app_descriptor.xml" ) ) }
		if( variant.buildType ) list.add( project.file( "${ moduleDir }/src/${ variant.buildType }/app_descriptor.xml" ) )

		list = list.reverse( )

		return list
	}
}
