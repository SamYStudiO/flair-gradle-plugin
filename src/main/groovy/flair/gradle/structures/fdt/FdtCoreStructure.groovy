package flair.gradle.structures.fdt

import flair.gradle.dependencies.Config
import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.structures.IStructure
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.Project
import org.gradle.api.file.FileTree

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FdtCoreStructure implements IStructure
{
	private Project project

	private File source

	private IExtensionManager extensionManager

	private String coreTemplate

	@Override
	public void create( Project project , File source )
	{

		this.project = project
		this.source = source

		extensionManager = project.flair as IExtensionManager

		File output = project.file( ".settings/com.powerflasher.fdt.core.prefs" )

		coreTemplate = project.file( "${ source.path }/fdt/core_template.prefs" ).text

		String content = coreTemplate

		output.write( createConfigurations( createCommonSettings( content ) ) )
	}

	private String createCommonSettings( String content )
	{
		List<String> list = content.readLines( )
		List<String> outputList = new ArrayList<String>( )

		list.eachWithIndex { line , index ->
			if( line.indexOf( "CompilerConstants" ) > 0 )
			{
				List<String> constants = new ArrayList<String>( )

				PluginManager.getCurrentPlatforms( project ).each {

					constants.add( "PLATFORM\\:\\:${ it.name.toUpperCase( ) }\\!false" )
				}

				extensionManager.allActivePlatformProductFlavors.each {

					constants.add( "PRODUCT_FLAVOR\\:\\:${ it.toUpperCase( ) }\\!false" )
				}

				extensionManager.allActivePlatformBuildTypes.each {

					constants.add( "BUILD_TYPE\\:\\:${ it.toUpperCase( ) }\\!false" )
				}

				outputList.push( line.split( "=" )[ 0 ] + "=${ constants.join( "\\n" ) }" )
			}
			else if( line.indexOf( "DefaultOutputFolder" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ project.buildDir.path.replace( project.rootDir.path + "\\" , "" ).replaceAll( "\\\\" , "/" ) }" )
			}
			else if( line.indexOf( "PlatformType" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ !PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? "MOBILE" : "AIR" }" )
			}
			else if( line.indexOf( "PlayerVersion" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ new Sdk( project ).version }" )
			}
			else if( line.indexOf( "ProjectTypeHint" ) > 0 )
			{
				String type = "AIR"
				if( PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) && !PluginManager.hasSinglePlatform( project ) ) type = "AIR All" else if( !PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ) type = "AIR Mobile"

				outputList.push( line.split( "=" )[ 0 ] + "=${ type }" )
			}
			else if( line.indexOf( "SdkName" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ new Sdk( project ).name }" )
			}
			else if( line.indexOf( "iOS.ACTIVE" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ PluginManager.hasPlatformPlugin( project , Platform.IOS ) }" )
			}
			else if( line.indexOf( "Android.ACTIVE" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) }" )
			}
			else if( line.indexOf( "AirDesktop.ACTIVE" ) > 0 )
			{
				outputList.push( line.split( "=" )[ 0 ] + "=${ PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) }" )
			}
			else
			{
				if( line.indexOf( "/com.powerflasher.fdt.core.mobile" ) < 0 && line.indexOf( "/id=" ) < 0 )
				{
					outputList.push( line )
				}
			}
		}

		return outputList.join( System.lineSeparator( ) )
	}

	private String createConfigurations( String content )
	{
		extensionManager.allActivePlatformVariants.each { variant ->

			List<File> libraries = new ArrayList<File>( )

			variant.getDirectories( Variant.NamingType.CAPITALIZE_BUT_FIRST ).each {

				String s = it == "main" ? Config.NATIVE_LIBRARY.name : it + Config.NATIVE_LIBRARY.name.capitalize( )

				libraries.addAll( project.configurations.getByName( s ).files )
			}

			libraries.each { file ->
				project.copy {

					from project.zipTree( file )
					into "${ source.path }/extracted_extensions/${ file.name }"
				}
			}

			FileTree extractedExtensions = project.fileTree( "${ source.path }/extracted_extensions/" )
			String extensions = ""

			extractedExtensions.each {

				if( it.name == "extension.xml" )
				{
					extensions += new XmlParser( ).parse( it ).id[ 0 ].text( ) + ","
				}
			}

			if( extensions.length( ) > 0 ) extensions.substring( 0 , extensions.length( ) - 1 )

			content += createConfigurationLine( variant , "AVAILABLE.EXTENSIONS" , extensions )
			content += createConfigurationLine( variant , "CUSTOM.ADT.CMD.LINE" , '${package} ${signing} ${target} ${connection} ${sampler} ${provision} ${output} ${content} ${icons}' )
			content += createConfigurationLine( variant , "CUSTOMIZE.ADT.ARGS" , "" )
			content += createConfigurationLine( variant , "DEPLOY.ARCH" , extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_X86 ) ? "Android-x86" : "" )
			if( variant.platform == Platform.ANDROID ) content += createConfigurationLine( variant , "DEPLOY.EXPORT.CAPTIVE" , "true" )
			if( variant.platform == Platform.ANDROID ) content += createConfigurationLine( variant , "DEPLOY.INSTALL.CHECK" , "false" )
			content += createConfigurationLine( variant , "DEPLOY.LOCATION" , buildPathFromRoot( project.file( "${ project.buildDir.path }/${ extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_FILE_NAME ) }.${ variant.platform.extension }" ).path ) )
			if( variant.platform == Platform.ANDROID ) content += createConfigurationLine( variant , "DEPLOY.MARKET.INDEX" , "0" )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "DEPLOY.PACKAGE.TYPE" , "0" )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "DEPLOY.PACKAGING.ENGINE" , "default" )
			content += createConfigurationLine( variant , "DEPLOY.PRE.PACKAGE.LAUNCHER" , "flair_" + variant.name )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "DEPLOY.SDK.LOCATION" , "" )
			if( variant.platform != Platform.IOS ) content += createConfigurationLine( variant , "DEPLOY.TYPE.AIR.DESKTOP" , "AIR" )
			if( variant.platform == Platform.ANDROID ) content += createConfigurationLine( variant , "ENABLE.DIGITAL.SIGNING" , "true" )
			content += createConfigurationLine( variant , "EXTENSIONS.LOCATION" , "" )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "EXTENSIONS.RESOLVE.IOS.SYMBOL.CONFLICTS" , extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_HIDE_ANE_LIB_SYMBOLS ).toString( ) )
			if( variant.platform == Platform.ANDROID ) content += createConfigurationLine( variant , "EXTRA.PACKAGING.OPTIONS" , "" )

			project.file( "${ project.buildDir.path }/${ variant.name }/package/icons" ).listFiles( ).each {

				String name = it.name.split( "\\." )[ 0 ]
				name = name.replace( "icon" , "" )

				content += createConfigurationLine( variant , "ICON.PATH.${ name }" , buildPathFromRoot( it.path ) )
			}

			content += createConfigurationLine( variant , "MOBILE.DESCRIPTOR" , buildPathFromRoot( project.file( "${ project.buildDir.path }/${ variant.name }/package/app_descriptor.xml" ).path ) )

			String packageContent = "${ buildPathFromRoot( project.buildDir.path ) }/${ variant.name }/package/app_descriptor.xml > app_descriptor.xml;${ buildPathFromRoot( project.buildDir.path ) }/${ variant.name }/package/${ variant.name }.swf > ${ variant.name }.swf;"

			project.file( "${ project.buildDir.path }/${ variant.name }/package" ).listFiles( ).each {

				if( it.name != "icons" && it.name.indexOf( ".swf" ) < 0 && it.name != "app_descriptor.xml" && it.name.indexOf( ".apk" ) < 0 && it.name.indexOf( ".ipa" ) < 0 && it.name.indexOf( ".exe" ) < 0 && it.name.indexOf( ".dmg" ) < 0 && it.name.indexOf( ".tmp" ) < 0 && it.name != "tmp" )
				{
					if( it.isDirectory( ) ) packageContent += buildPathFromRoot( it.path ) + ";" else packageContent += "${ buildPathFromRoot( it.path ) } > ${ it.name };"
				}
			}

			content += createConfigurationLine( variant , "PACKAGE.CONTENT" , packageContent )
			content += createConfigurationLine( variant , "SIGNATURE.CERTIFICATE" , buildPathFromRoot( extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_KEY_STORE ).toString( ) ) )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "SIGNATURE.CERTIFICATE.RELEASE" , "" )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "SIGNATURE.PROVISIONING.FILE" , buildPathFromRoot( extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_PROVISIONING_PROFILE ).toString( ) ) )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "SIGNATURE.PROVISIONING.FILE.RELEASE" , "" )
			content += createConfigurationLine( variant , "SIGNATURE.PWD" , "LOCAL" )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "SIGNATURE.PWD.RELEASE" , "" )
			if( variant.platform == Platform.IOS ) content += createConfigurationLine( variant , "SIGNATURE.USE.DIFEERENT.PROFILES" , "false" )
			content += createConfigurationLine( variant , "SWFPATH" , "" )
			content += createConfigurationIdLine( variant )
		}

		return content
	}

	private String createConfigurationLine( Variant variant , String prop , String value )
	{
		String s = ""

		switch( variant.platform )
		{
			case Platform.IOS:
				s = "iOS(${ variant.name })/com.powerflasher.fdt.core.mobile.iOS."
				break

			case Platform.ANDROID:
				s = "Android(${ variant.name })/com.powerflasher.fdt.core.mobile.Android."
				break

			case Platform.DESKTOP:
				s = "Desktop(${ variant.name })/com.powerflasher.fdt.core.mobile.AirDesktop."
				break
		}

		s += prop + "=" + value + System.lineSeparator( )

		return s
	}

	private String createConfigurationIdLine( Variant variant )
	{
		String s = ""

		switch( variant.platform )
		{
			case Platform.IOS:
				s = "iOS"
				break

			case Platform.ANDROID:
				s = "Android"
				break

			case Platform.DESKTOP:
				s = "Desktop"
				break
		}

		s += "(${ variant.name })/id=${ variant.name }" + System.lineSeparator( )

		return s
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
