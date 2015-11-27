package com.flair.tasks

import groovy.util.slurpersupport.GPathResult
import groovy.util.slurpersupport.NodeChildren
import groovy.xml.XmlUtil
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
		String moduleName = project.flair.moduleName

		updatePropertiesFromFile( project.file( "${ moduleName }/src/main/resources/android/app_descriptor.xml" ) )
		updatePropertiesFromFile( project.file( "${ moduleName }/src/main/resources/ios/app_descriptor.xml" ) )
		updatePropertiesFromFile( project.file( "${ moduleName }/src/main/resources/desktop/app_descriptor.xml" ) , true )

		String commonResources = project.flair.commonResources
		String androidResources = project.flair.androidResources
		String iosResources = project.flair.iosResources
		String desktopResources = project.flair.desktopResources

		GPathResult root = new XmlSlurper( ).parse( "${ moduleName }/${ moduleName }.iml" )

		root.component.configurations.configuration.each { conf ->

			String subNodeName = ""
			String typeResources = ""

			if( conf.@name.toString( ).indexOf( "android" ) >= 0 )
			{
				subNodeName = "packaging-android"
				typeResources = androidResources
			}
			if( conf.@name.toString( ).indexOf( "ios" ) >= 0 )
			{
				subNodeName = "packaging-ios"
				typeResources = iosResources
			}
			if( conf.@name.toString( ).indexOf( "desktop" ) >= 0 )
			{
				subNodeName = "packaging-air-desktop"
				typeResources = desktopResources
			}

			NodeChildren rootFileToPackage = conf."${ subNodeName }"."files-to-package"

			rootFileToPackage.FilePathAndPathInPackage.each { file ->

				if( file.@"file-path".toString( ).indexOf( "icons" ) < 0 && file.@"file-path".toString( ).indexOf( "splashs" ) < 0 )
				{
					file.replaceNode {}
				}
			}

			FileTree tree = project.fileTree( "${ moduleName }/src/main/resources/" )

			tree.each { file ->
				File parent = file.getParentFile( )
				String parentName = parent.getName( )

				String[] commons = commonResources.split( "," )
				String[] types = typeResources.split( "," )

				commons.each { common ->
					if( parent.getParentFile( ).getName( ) == "resources" && parentName.matches( common ) )
					{
						rootFileToPackage.appendNode( new XmlSlurper( ).parseText( "<FilePathAndPathInPackage file-path=\"\$MODULE_DIR\$/src/main/resources/${ parentName }\" path-in-package=\"resources/${ parentName }\" />" ) )
					}
				}

				types.each { type ->
					if( parent.getParentFile( ).getName( ) == "resources" && parentName.matches( type ) )
					{
						rootFileToPackage.appendNode( new XmlSlurper( ).parseText( "<FilePathAndPathInPackage file-path=\"\$MODULE_DIR\$/src/main/resources/${ parentName }\" path-in-package=\"resources/${ parentName }\" />" ) )
					}
				}
			}
		}

		project.file( "${ moduleName }/${ moduleName }.iml" ).write( XmlUtil.serialize( root ) )
	}

	private void updatePropertiesFromFile( File f )
	{
		updatePropertiesFromFile( f , false )
	}

	private void updatePropertiesFromFile( File f , Boolean desktop )
	{
		String appId = project.flair.appId
		String appAspectRatio = project.flair.appAspectRatio
		String appAutoOrient = project.flair.appAutoOrient
		String appDepthAndStencil = project.flair.appDepthAndStencil
		String appContent = f.getText( )
		String supportedLocales = getSupportedLocales( )

		if( desktop )
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
					.replaceAll( /<depthAndStencil>.*<\\/depthAndStencil>/ , "<depthAndStencil>${ appDepthAndStencil }</depthAndStencil>" )
					.replaceAll( /<supportedLanguages>.*<\\/supportedLanguages>/ , "<supportedLanguages>${ supportedLocales }</supportedLanguages>" )
		}
		else
		{
			appContent = appContent.replaceAll( /<id>.*<\\/id>/ , "<id>${ appId }</id>" )
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
		String allowLocales = "en de cs es fr it ja ko nl pl pt ru sv tr zh"
		String supportedLocales = ""

		tree.each { file ->

			if( file.getName( ) == "strings.xml" )
			{
				String parent = file.getParentFile( ).getName( )

				if( parent.indexOf( "-" ) > 0 )
				{
					String locale = parent.split( "-" )[ 1 ].toLowerCase( )

					if( allowLocales.indexOf( locale ) >= 0 ) supportedLocales = supportedLocales.concat( locale + " " )
				}
			}
		}

		String defaultLocale = project.flair.defaultLocale.toLowerCase( )
		if( !defaultLocale.isEmpty( ) && allowLocales.indexOf( allowLocales ) >= 0 && supportedLocales.indexOf( defaultLocale ) < 0 ) supportedLocales = supportedLocales.concat( defaultLocale )

		return supportedLocales.trim( )
	}
}
