package com.flair.tasks

import com.flair.utils.SDKManager
import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 25/11/2015.
 */
class UpdateProperties extends DefaultTask
{
	public UpdateProperties()
	{
		group = "scaffold"
		description = ""
	}

	@TaskAction
	public void updateProperties()
	{
		String appId = project.flair.appId

		if( appId.isEmpty( ) ) throw new IllegalArgumentException( "Missing appId property add\nflair {\n	appId = \"myAppid\"\n}\nto your build.gradle file." )

		String moduleName = project.flair.moduleName

		FileTree tree = project.fileTree( "${ moduleName }/src/main/" )
		tree.each { file ->

			if( file.getText( ).indexOf( "<application xmlns=\"http://ns.adobe.com/air/application/" ) > 0 ) updatePropertiesFromFile( file )
		}

		String commonResources = project.flair.commonResources
		String androidResources = project.flair.androidResources
		String iosResources = project.flair.iosResources
		String desktopResources = project.flair.desktopResources

		File iml = project.file( "${ moduleName }/${ moduleName }.iml" )
		String imlContent = iml.getText( )
		String out = ""
		Boolean replace = false
		String subNodeName = ""
		String typeResources = ""

		imlContent.eachLine { line ->

			if( line.indexOf( "configuration" ) >= 0 )
			{
				if( line.indexOf( "android" ) >= 0 )
				{
					subNodeName = "packaging-android"
					typeResources = androidResources
				}
				if( line.indexOf( "ios" ) >= 0 )
				{
					subNodeName = "packaging-ios"
					typeResources = iosResources
				}
				if( line.indexOf( "desktop" ) >= 0 )
				{
					subNodeName = "packaging-air-desktop"
					typeResources = desktopResources
				}

				out += line + "\r\n"
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
						File parent = file.getParentFile( )
						String parentName = parent.getName( )

						String[] commons = commonResources.split( "," )
						String[] types = typeResources.split( "," )

						commons.each { common ->

							String path = "            <FilePathAndPathInPackage file-path=\"\$MODULE_DIR\$/src/main/resources/${ parentName }\" path-in-package=\"resources/${ parentName }\" />\r\n"

							if( parent.getParentFile( ).getName( ) == "resources" && parentName.matches( common.replace( "/**" , "" ).replace( "**/" , "" ).replace( "*" , ".*" ) ) && paths.indexOf( path ) < 0 )
							{
								paths += path
							}
						}

						types.each { type ->

							String path = "            <FilePathAndPathInPackage file-path=\"\$MODULE_DIR\$/src/main/resources/${ parentName }\" path-in-package=\"resources/${ parentName }\" />\r\n"

							if( parent.getParentFile( ).getName( ) == "resources" && parentName.matches( type.replace( "/**" , "" ).replace( "**/" , "" ).replace( "*" , ".*" ) ) && paths.indexOf( path ) < 0 )
							{
								paths += path
							}
						}
					}

					out += paths
				}

				out += line + "\r\n"
			}
		}

		iml.write( out )
	}

	protected void updatePropertiesFromFile( File f )
	{
		String sdkVersion = SDKManager.getVersion( project )
		String appId = project.flair.appId
		String appName = project.flair.appName
		String appAspectRatio = project.flair.appAspectRatio
		String appAutoOrient = project.flair.appAutoOrient.toString( )
		String appDepthAndStencil = project.flair.appDepthAndStencil.toString( )
		String appContent = f.getText( )
		String supportedLocales = getSupportedLocales( )
		Boolean desktop = f.getText( ).indexOf( "<android>" ) < 0 && f.getText( ).indexOf( "<iPhone>" ) < 0

		if( desktop )
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
					.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}
		else
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<application xmlns=".*">/ , "<application xmlns=\"http://ns.adobe.com/air/application/${ sdkVersion }\">" )
					.replaceAll( /<name>.*<\\/name>/ , "<name>${ appName }</name>" )
					.replaceAll( /<aspectRatio>.*<\\/aspectRatio>/ , "<aspectRatio>${ appAspectRatio }</aspectRatio>" )
					.replaceAll( /<autoOrients>.*<\\/autoOrients>/ , "<autoOrients>${ appAutoOrient }</autoOrients>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}

		f.write( appContent )
	}

	private String getSupportedLocales()
	{
		String moduleName = project.flair.moduleName

		FileTree tree = project.fileTree( "${ moduleName }/src/main/resources/" )
		String locales = "en de cs es fr it ja ko nl pl pt ru sv tr zh"
		String supportedLocales = ""

		tree.each { file ->

			if( file.getName( ) == "strings.xml" )
			{
				String parent = file.getParentFile( ).getName( )

				if( parent.indexOf( "-" ) > 0 )
				{
					String locale = parent.split( "-" )[ 1 ].toLowerCase( )

					if( locales.indexOf( locale ) >= 0 ) supportedLocales = supportedLocales.concat( locale + " " )
				}
			}
		}

		String defaultLocale = project.flair.defaultLocale.toLowerCase( )
		if( !defaultLocale.isEmpty( ) && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}
