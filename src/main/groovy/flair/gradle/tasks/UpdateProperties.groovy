package flair.gradle.tasks

import flair.gradle.extensions.ConfigurationExtension
import flair.gradle.extensions.PropertyManager
import flair.gradle.variants.Platform
import flair.gradle.utils.AIRSDKManager
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class UpdateProperties extends AbstractVariantTask
{
	public UpdateProperties()
	{
		group = Group.SCAFFOLD.name
		description = ""
	}

	@TaskAction
	public void updateProperties()
	{
		//String packageName = PropertyManager.getProperty( project , "packageName" )

		//if( packageName.isEmpty( ) ) throw new IllegalArgumentException( String.format( "Missing packageName property add%nflair {%n	packageName = \"myAppid\"%n}%nto your build.gradle file." ) )

		String version = PropertyManager.getProperty( project , ConfigurationExtension.APP_DESCRIPTOR.name , "version" , platform , productFlavor , buildType )

		if( version.isEmpty( ) ) throw new IllegalArgumentException( String.format( "Missing appVersion add%nflair {%n\\appVersion = \"x.x.x\"%n}%nto your build.gradle file." ) )

		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		String[] a = version.split( "\\." )

		String major = a.length > 0 ? a[ 0 ] : "0"
		String minor = a.length > 1 ? a[ 1 ] : "0"
		String build = a.length > 2 ? a[ 2 ] : "0"

		FileTree tree = project.fileTree( "${ moduleName }/src/main/" ) {
			include "**/*.xml"
		}
		tree.each { file ->

			if( file.getText( ).indexOf( "<application xmlns=\"http://ns.adobe.com/air/application/" ) > 0 ) updatePropertiesFromFile( file , "${ major }.${ minor }.${ build }" )
		}

		String iosExcludeResources = PropertyManager.getProperty( project , "excludeResources" , Platform.IOS )
		String androidExcludeResources = PropertyManager.getProperty( project , "excludeResources" , Platform.ANDROID )
		String desktopExcludeResources = PropertyManager.getProperty( project , "excludeResources" , Platform.DESKTOP )

		File iml = project.file( "${ moduleName }/${ moduleName }.iml" )
		String imlContent = iml.getText( )
		String out = ""
		boolean replace = false
		String typeExcludeResources = ""

		imlContent.eachLine { line ->

			if( line.indexOf( "air_sdk_" ) >= 0 ) out += line.replaceAll( /air_sdk_\d{2}\.\d/ , AIRSDKManager.getPath( project ).split( "/" ).last( ) ) + System.lineSeparator( ) else if( line.indexOf( "configuration" ) >= 0 )
			{
				if( line.indexOf( "android" ) >= 0 )
				{
					typeExcludeResources = androidExcludeResources
				}
				if( line.indexOf( "ios" ) >= 0 )
				{
					typeExcludeResources = iosExcludeResources
				}
				if( line.indexOf( "desktop" ) >= 0 )
				{
					typeExcludeResources = desktopExcludeResources
				}

				out += line + System.lineSeparator( )
			}
			else if( line.indexOf( "packaging" ) >= 0 )
			{
				out += line.replaceAll( /_[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}/ , "_${ major }.${ minor }.${ build }" ) + System.lineSeparator( )
			}
			else if( line.indexOf( "FilePathAndPathInPackage" ) >= 0 && line.indexOf( "splashs" ) < 0 && line.indexOf( "icons" ) < 0 )
			{
				replace = true
			}
			else
			{
				if( replace )
				{
					replace = false

					tree = project.fileTree( "${ moduleName }/src/main/resources/" )
					String paths = ""

					tree.each { file ->

						if( file.getParentFile( ).getName( ) != "resources" )
						{
							File parent = file.getParentFile( )

							while( parent.getParentFile( ).getName( ) != "resources" )
							{
								parent = parent.getParentFile( )
							}

							String parentName = parent.getName( )

							String[] types = typeExcludeResources.split( "," )

							types.each { type ->

								String path = "            <FilePathAndPathInPackage file-path=\"\$MODULE_DIR\$/src/main/resources/${ parentName }\" path-in-package=\"resources/${ parentName }\" />" + System.lineSeparator( )

								if( parent.getParentFile( ).getName( ) == "resources" && !parentName.matches( type.replace( "/**" , "" ).replace( "**/" , "" ).replace( "*" , ".*" ) ) && paths.indexOf( path ) < 0 )
								{
									paths += path
								}
							}
						}
					}

					out += paths
				}

				out += line + System.lineSeparator( )
			}
		}

		iml.write( out )
	}

	private void updatePropertiesFromFile( File f , String version )
	{
		String sdkVersion = AIRSDKManager.getVersion( project )
		String appId = PropertyManager.getProperty( project , "appDescriptor" , "id" , platform , productFlavor , buildType )
		String appName = PropertyManager.getProperty( project , "appDescriptor" , "appName" , platform , productFlavor , buildType )
		String appFullScreen = PropertyManager.getProperty( project , "appDescriptor" , "fullScreen" , platform , productFlavor , buildType )
		String appAspectRatio = PropertyManager.getProperty( project , "appDescriptor" , "aspectRatio" , platform , productFlavor , buildType )
		String appAutoOrient = PropertyManager.getProperty( project , "appDescriptor" , "autoOrient" , platform , productFlavor , buildType )
		String appDepthAndStencil = PropertyManager.getProperty( project , "appDescriptor" , "depthAndStencil" , platform , productFlavor , buildType )
		String appContent = f.getText( )
		String supportedLocales = getSupportedLocales( )
		boolean desktop = f.getText( ).indexOf( "<android>" ) < 0 && f.getText( ).indexOf( "<iPhone>" ) < 0

		if( desktop )
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
					.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ version }</versionNumber>" )
					.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}
		else
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
					.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
					.replaceAll( /<versionNumber>.*<\\/versionNumber>/ , "<versionNumber>${ version }</versionNumber>" )
					.replaceAll( /<fullScreen>.*<\\/fullScreen>/ , "<fullScreen>${ appFullScreen }</fullScreen>" )
					.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
					.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrient }</autoOrients>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}

		f.write( appContent )
	}

	private String getSupportedLocales()
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		FileTree tree = project.fileTree( "${ moduleName }/src/main/resources/" ) {
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

		String defaultLocale = PropertyManager.getProperty( project , "appDescriptor" , "defaultSupportedLanguages" , platform , productFlavor , buildType )
		if( !defaultLocale.isEmpty( ) && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}
