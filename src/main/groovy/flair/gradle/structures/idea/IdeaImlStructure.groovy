package flair.gradle.structures.idea

import flair.gradle.dependencies.Config
import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.structures.IStructure
import flair.gradle.utils.Platform
import groovy.xml.XmlUtil
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class IdeaImlStructure implements IStructure
{
	private Project project

	private String moduleName

	private IExtensionManager extensionManager

	private String configurationTemplate

	private String sdkTemplate

	@Override
	public void create( Project project , File source )
	{
		this.project = project

		extensionManager = project.flair as IExtensionManager
		moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )
		configurationTemplate = project.file( "${ source.path }/idea/configuration_template.xml" ).text
		sdkTemplate = project.file( "${ source.path }/idea/sdk_template.xml" ).text

		File output = project.file( "${ moduleName }/${ moduleName }.iml" )
		File file = !output.exists( ) ? project.file( "${ source.path }/idea/template.iml" ) : output

		Node xml = new XmlParser( ).parse( file )
		Node flexBuildConfigurationManager = xml.children( ).find { it.@name == "FlexBuildConfigurationManager" } as Node
		Node newModuleRootManager = xml.children( ).find { it.@name == "NewModuleRootManager" } as Node
		Node content = newModuleRootManager.content[ 0 ] as Node

		if( !content ) content = new Node( newModuleRootManager , "content" )

		createSourceFolders( content )
		createExcludeFolders( content )
		createLibraries( newModuleRootManager )
		createConfigurations( flexBuildConfigurationManager.configurations[ 0 ] as Node )

		output.withOutputStream { writer -> XmlUtil.serialize( xml , writer ) }

		List<String> sdks = new ArrayList<String>( )

		Platform.values( ).each {

			String name = new Sdk( project , it ).name
			if( !sdks.contains( name ) ) sdks.push( name )
		}

		String s = output.text.replaceAll( "#09;" , "&#09;" ).replaceAll( /jdkName=".*" j/ , "jdkName=\"${ sdks.join( "&#9;" ) }\" j" ).replaceAll( /jdkType=".*"/ , "jdkType=\"${ sdks.size( ) > 1 ? "__CompositeFlexSdk__" : "Flex SDK Type (new)" }\"" )

		if( !flexBuildConfigurationManager.configurations[ 0 ].children( ).find { it.@name == flexBuildConfigurationManager.@active } )
		{
			s = s.replaceAll( "\\{activeConfiguration\\}" , flexBuildConfigurationManager.configurations[ 0 ].children( )[ 0 ].@name.toString( ) )
		}

		output.write( s )
	}

	private String buildPathFromModule( String path , boolean escape = false )
	{
		path = path.replaceAll( "\\\\" , "/" )
		String modulePath = project.file( moduleName ).path.replaceAll( "\\\\" , "/" )

		if( path.startsWith( modulePath ) )
		{
			return path.replace( modulePath , escape ? '\\$MODULE_DIR\\$' : '$MODULE_DIR$' )
		}
		else
		{
			int count = 0

			while( !path.startsWith( modulePath ) && modulePath.indexOf( "/" ) > 0 )
			{
				List<String> a = modulePath.split( "/" ).toList( )

				a.pop( )

				modulePath = a.join( "/" )

				count++
			}

			if( path.startsWith( modulePath ) )
			{
				String up = ""

				for( int i = 0; i < count; i++ ) up += "../"

				return ( escape ? '\\$MODULE_DIR\\$/' : '$MODULE_DIR$/' ) + up + path.replace( modulePath + "/" , "" )
			}
		}

		return path.replaceAll( "\\\\" , "/" )
	}

	private void createSourceFolders( Node xml )
	{
		xml.children( ).findAll { it.name( ) == "sourceFolder" }.each { it.parent( ).remove( it ) }

		List<String> list = new ArrayList<String>( )

		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.SOURCE.name ) }.each {

			it.files.each { file ->

				if( file.exists( ) )
				{
					if( !list.contains( file.path ) )
					{
						new Node( xml , "sourceFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" , isTestSource: "false" ] )
						list.add( file.path )
					}
				}
			}
		}
	}

	private void createExcludeFolders( Node xml )
	{
		xml.children( ).findAll { it.name( ) == "excludeFolder" }.each { it.parent( ).remove( it ) }

		File file = project.file( "${ moduleName }/src/main/atlases" )

		if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

		List<String> list = new ArrayList<String>( )

		PluginManager.getCurrentPlatforms( project ).each { list.add( it.name ) }
		list.addAll( extensionManager.allActivePlatformProductFlavors )
		list.addAll( extensionManager.allActivePlatformBuildTypes )

		list.each {
			file = project.file( "${ moduleName }/src/${ it }/icons" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

			file = project.file( "${ moduleName }/src/${ it }/splash_screens" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

			file = project.file( "${ moduleName }/src/${ it }/signing" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

			file = project.file( "${ moduleName }/src/${ it }/atlases" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )
		}
	}

	private void createLibraries( Node moduleRootManagerNode )
	{
		project.configurations.findAll { it.name.toLowerCase( ).contains( Config.LIBRARY.name ) }.each {

			it.files.each { file ->

				if( file.exists( ) )
				{
					String path = file.isDirectory( ) ? file.path : file.parentFile.path
					String name = project.file( path ).name

					if( !moduleRootManagerNode.children( ).find { it.name( ) == "orderEntry" && it.@name == name } )
					{
						new Node( moduleRootManagerNode , "orderEntry" , [ type: "library" , exported: "" , name: "${ project.file( path ).name }" , level: "project" ] )
					}
				}
			}
		}
	}

	private void createConfigurations( Node configurationsNode )
	{
		configurationsNode.children( ).findAll { it.@name == null || it.@name.startsWith( "flair_" ) }.each { it.parent( ).remove( it ) }

		extensionManager.allActivePlatformVariants.each { variant ->

			String platform = ""

			switch( variant.platform )
			{
				case Platform.IOS:
					platform = "ios"
					break

				case Platform.ANDROID:
					platform = "android"
					break

				case Platform.DESKTOP:
					platform = "air-desktop"
					break
			}

			String configuration = configurationTemplate

			List<String> constants = new ArrayList<String>( )


			PluginManager.getCurrentPlatforms( project ).each {

				constants.add( "PLATFORM::${ it.name.toUpperCase( ) }#09;${ it == variant.platform }" )
			}

			extensionManager.allActivePlatformProductFlavors.each {

				constants.add( "PRODUCT_FLAVOR::${ it.toUpperCase( ) }#09;${ variant.productFlavors.indexOf( it ) >= 0 }" )
			}

			extensionManager.allActivePlatformBuildTypes.each {

				constants.add( "BUILD_TYPE::${ it.toUpperCase( ) }#09;${ it == variant.buildType }" )
			}

			configuration = configuration.replaceAll( "\\{configurationName\\}" , "flair_" + variant.name )
					.replaceAll( "\\{platform\\}" , platform )
					.replaceAll( "\\{mainClass\\}" , extensionManager.getFlairProperty( variant , FlairProperty.COMPILER_MAIN_CLASS ) as String )
					.replaceAll( "\\{outputSwf\\}" , variant.name + ".swf" )
					.replaceAll( "\\{buildDir\\}" , buildPathFromModule( project.buildDir.path , true ) + "/" + variant.name + "/package" )
					.replaceAll( "\\{target\\}" , variant.platform == Platform.DESKTOP ? "Desktop" : "Mobile" )
					.replaceAll( "\\{sdkName\\}" , new Sdk( project , variant.platform ).name )
					.replaceAll( "\\{debug\\}" , extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ) ? "true" : "false" )
					.replaceAll( "\\{constants\\}" , constants.join( "&#10;" ) )
					.replaceAll( "\\{compilerOptions\\}" , ( extensionManager.getFlairProperty( variant , FlairProperty.COMPILER_OPTIONS ) as List ).join( " " ) )

			Node configurationNode = new XmlParser( ).parseText( configuration )

			Node platformNode = null

			switch( variant.platform )
			{
				case Platform.IOS:
					platformNode = configurationNode."packaging-ios"[ 0 ] as Node
					break

				case Platform.ANDROID:
					platformNode = configurationNode."packaging-android"[ 0 ] as Node
					break

				case Platform.DESKTOP:
					platformNode = configurationNode."packaging-air-desktop"[ 0 ] as Node
					break
			}

			platformNode.@"enabled" = "true"
			platformNode.@"use-generated-descriptor" = "false"
			platformNode.@"custom-descriptor-path" = buildPathFromModule( project.file( "${ project.buildDir.path }/${ variant.name }/package/app_descriptor.xml" ).path )
			platformNode.@"package-file-name" = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_FILE_NAME )

			if( platformNode."files-to-package"[ 0 ] == null ) new Node( platformNode , "files-to-package" )

			Node toPackage = platformNode."files-to-package"[ 0 ] as Node

			toPackage.children( ).each { it.parent( ).remove( it ) }

			project.file( "${ project.buildDir.path }/${ variant.name }/package" ).listFiles( ).each {

				if( it.name != "${ variant.name }.swf" && it.name != "app_descriptor.xml" && it.name.indexOf( ".apk" ) < 0 && it.name.indexOf( ".ipa" ) < 0 && it.name.indexOf( ".exe" ) < 0 && it.name.indexOf( ".dmg" ) < 0 && it.name.indexOf( ".tmp" ) < 0 )
				{
					Node node = new Node( toPackage , "FilePathAndPathInPackage" )

					node.@"file-path" = buildPathFromModule( it.path )
					node.@"path-in-package" = it.name
				}
			}

			Node signingNode = platformNode.AirSigningOptions[ 0 ] as Node
			if( signingNode == null ) signingNode = new Node( platformNode , "AirSigningOptions" )

			String signingAlias = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_ALIAS )
			String signingStoreType = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_STORE_TYPE )
			String signingKeyStore = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_KEY_STORE )
			String signingProviderName = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_PROVIDER_NAME )
			String signingTsa = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_TSA )
			String signingProvisioningProfile = extensionManager.getFlairProperty( variant , FlairProperty.SIGNING_PROVISIONING_PROFILE )
			boolean x86 = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_X86 )

			if( signingAlias ) signingNode."@key-alias" = signingAlias
			if( signingStoreType ) signingNode."@keystore-type" = signingStoreType
			if( signingProviderName ) signingNode."@provider" = signingProviderName
			if( signingProvisioningProfile ) signingNode."@provisioning-profile-path" = buildPathFromModule( signingProvisioningProfile )
			if( signingKeyStore ) signingNode."@keystore-path" = buildPathFromModule( signingKeyStore )
			if( signingTsa ) signingNode."@tsa" = signingTsa
			if( variant.platform == Platform.ANDROID && x86 ) signingNode."@arch" = "x86"
			signingNode."@use-temp-certificate" = extensionManager.getFlairProperty( variant , FlairProperty.DEBUG ).toString( )

			List<Configuration> libraries = new ArrayList<Configuration>( )

			project.configurations.each {

				boolean isLibrary = it.name.toLowerCase( ).contains( Config.LIBRARY.name )

				if( it.name == Config.AS_LIBRARY.name || it.name == Config.LIBRARY.name || it.name == Config.NATIVE_LIBRARY.name ) libraries.add( it )
				if( it.name.contains( variant.platform.name ) && isLibrary ) libraries.add( it )

				for( String flavor : variant.productFlavors )
				{
					if( it.name.contains( flavor ) && isLibrary ) libraries.add( it )
				}

				if( variant.buildType && it.name.contains( variant.buildType ) && isLibrary ) libraries.add( it )
			}

			List<String> added = new ArrayList<String>( )

			libraries.each { conf ->

				conf.files.each { file ->

					if( file.exists( ) )
					{
						String path = file.isDirectory( ) ? file.path : file.parentFile.path

						if( !added.contains( path ) )
						{
							Node dependencies = configurationNode.dependencies[ 0 ] as Node

							Node entries = dependencies.entries[ 0 ] as Node
							if( !entries ) entries = new Node( dependencies , "entries" )

							Node entry = new Node( entries , "entry" )
							entry."@library-name" = project.file( path ).name
							entry."@library-level" = "project"
							new Node( entry , "dependency" , [ linkage: "Merged" ] )
							added.add( path )
						}
					}
				}
			}

			configurationsNode.append( configurationNode )
		}
	}
}