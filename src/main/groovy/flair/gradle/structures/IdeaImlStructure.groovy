package flair.gradle.structures

import flair.gradle.dependencies.Configurations
import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import groovy.xml.XmlUtil
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaImlStructure implements IStructure
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
		moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name )
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
		createConfigurations( flexBuildConfigurationManager.configurations[ 0 ] as Node , newModuleRootManager )
		createSdk( )

		output.withWriter { writer -> XmlUtil.serialize( xml , writer ) }

		output.write( output.text.replaceAll( "#09;" , "&#09;" ) )
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

		project.configurations.findAll {
			it.name.toLowerCase( ).contains( "compile" ) && !it.name.toLowerCase( ).contains( "librarycompile" ) && !it.name.toLowerCase( ).contains( "nativecompile" )
		}.each {

			it.files.each { file ->

				if( file.exists( ) )
				{
					String path = file.isDirectory( ) ? file.path : file.parentFile.path

					if( !list.contains( path ) ) new Node( xml , "sourceFolder" , [ url: "file://${ buildPathFromModule( path ) }" , isTestSource: "false" ] )

					list.add( path )
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

		extensionManager.allActivePlatformProductFlavors.each { list.add( it.name ) }
		extensionManager.allActivePlatformBuildTypes.each { list.add( it.name ) }

		list.each {
			file = project.file( "${ moduleName }/src/${ it }/icons" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

			file = project.file( "${ moduleName }/src/${ it }/splashs" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

			file = project.file( "${ moduleName }/src/${ it }/signing" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )

			file = project.file( "${ moduleName }/src/${ it }/atlases" )
			if( file.exists( ) ) new Node( xml , "excludeFolder" , [ url: "file://${ buildPathFromModule( file.path ) }" ] )
		}
	}

	private void createConfigurations( Node xml , Node newModuleRootManager )
	{
		xml.children( ).findAll { it.@name == null || it.@name.startsWith( "flair_" ) }.each { it.parent( ).remove( it ) }

		extensionManager.allActivePlatformVariants.each { variant ->

			String platform = ""

			switch( variant.platform )
			{
				case Platforms.IOS:
					platform = "ios"
					break

				case Platforms.ANDROID:
					platform = "android"
					break

				case Platforms.DESKTOP:
					platform = "air-desktop"
					break
			}

			String configuration = configurationTemplate

			List<String> constants = new ArrayList<String>( )

			Platforms.values( ).each {

				constants.add( "PLATFORM::${ it.name.toUpperCase( ) }#09;${ it == variant.platform }" )
			}

			extensionManager.allActivePlatformProductFlavors.each {

				constants.add( "PRODUCT_FLAVOR::${ it.name.toUpperCase( ) }#09;${ variant.productFlavors.indexOf( it.name ) >= 0 }" )
			}

			extensionManager.allActivePlatformBuildTypes.each {

				constants.add( "BUILD_TYPE::${ it.name.toUpperCase( ) }#09;${ it.name == variant.buildType }" )
			}

			configuration = configuration.replaceAll( "\\{configurationName\\}" , "flair_" + variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) )
					.replaceAll( "\\{platform\\}" , platform )
					.replaceAll( "\\{mainClass\\}" , extensionManager.getFlairProperty( variant , FlairProperties.COMPILE_MAIN_CLASS.name ) as String )
					.replaceAll( "\\{outputSwf\\}" , variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + ".swf" )
					.replaceAll( "\\{buildDir\\}" , buildPathFromModule( project.buildDir.path , true ) + "/" + variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + "/package" )
					.replaceAll( "\\{target\\}" , variant.platform == Platforms.DESKTOP ? "Desktop" : "Mobile" )
					.replaceAll( "\\{sdkName\\}" , new Sdk( project ).name )
					.replaceAll( "\\{debug\\}" , extensionManager.getFlairProperty( variant , FlairProperties.DEBUG.name ) ? "true" : "false" )
					.replaceAll( "\\{constants\\}" , constants.join( "&#10;" ) )
					.replaceAll( "\\{compilerOptions\\}" , ( extensionManager.getFlairProperty( variant , FlairProperties.COMPILE_OPTIONS.name ) as List ).join( " " ) )

			Node configurationNode = new XmlParser( ).parseText( configuration )

			Node platformNode = null

			switch( variant.platform )
			{
				case Platforms.IOS:
					platformNode = configurationNode."packaging-ios"[ 0 ] as Node
					break

				case Platforms.ANDROID:
					platformNode = configurationNode."packaging-android"[ 0 ] as Node
					break

				case Platforms.DESKTOP:
					platformNode = configurationNode."packaging-air-desktop"[ 0 ] as Node
					break
			}

			platformNode.@"enabled" = "true"
			platformNode.@"use-generated-descriptor" = "false"
			platformNode.@"custom-descriptor-path" = buildPathFromModule( project.file( "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }/app_descriptor.xml" ).path )
			platformNode.@"package-file-name" = "${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }_${ extensionManager.getFlairProperty( variant , FlairProperties.APP_VERSION.name ) }"

			if( platformNode."files-to-package"[ 0 ] == null ) new Node( platformNode , "files-to-package" )

			Node toPackage = platformNode."files-to-package"[ 0 ] as Node

			toPackage.children( ).each { it.parent( ).remove( it ) }


			project.file( "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }/package" ).listFiles( ).each {

				if( it.name != "${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" && it.name != "app_descriptor.xml" )
				{
					Node node = new Node( toPackage , "FilePathAndPathInPackage" )

					node.@"file-path" = buildPathFromModule( it.path )
					node.@"path-in-package" = it.name
				}
			}

			Node signingNode = platformNode.AirSigningOptions[ 0 ] as Node
			if( signingNode == null ) signingNode = new Node( platformNode , "AirSigningOptions" )

			String signingAlias = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_ALIAS.name )
			String signingStoreType = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_STORE_TYPE.name )
			String signingKeyStore = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_KEY_STORE.name )
			//String signingStorePass = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_STORE_PASS.name )
			//String signingKeyPass = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_KEY_PASS.name )
			String signingProviderName = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_PROVIDER_NAME.name )
			String signingTsa = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_TSA.name )
			String signingProvisioningProfile = extensionManager.getFlairProperty( variant , FlairProperties.SIGNING_PROVISIONING_PROFILE.name )
			boolean x86 = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_X86.name )

			if( signingAlias ) signingNode."@key-alias" = signingAlias
			if( signingStoreType ) signingNode."@keystore-type" = signingStoreType
			if( signingProviderName ) signingNode."@provider" = signingProviderName
			if( signingProvisioningProfile ) signingNode."@provisioning-profile-path" = buildPathFromModule( signingProvisioningProfile )
			if( signingKeyStore ) signingNode."@keystore-path" = buildPathFromModule( signingKeyStore )
			if( signingTsa ) signingNode."@tsa" = signingTsa
			if( variant.platform == Platforms.ANDROID && x86 ) signingNode."@arch" = "x86"
			signingNode."@use-temp-certificate" = "false"

			List<Configuration> libraries = new ArrayList<Configuration>( )

			project.configurations.each {

				boolean libraryOrNative = it.name.toLowerCase( ).contains( "librarycompile" ) || it.name.toLowerCase( ).contains( "nativecompile" )

				if( it.name == Configurations.AS_LIBRARY_COMPILE.name || it.name == Configurations.LIBRARY_COMPILE.name || it.name == Configurations.NATIVE_COMPILE.name ) libraries.add( it )
				if( it.name.contains( variant.platform.name ) && libraryOrNative ) libraries.add( it )

				for( String flavor : variant.productFlavors )
				{
					if( it.name.contains( flavor ) && libraryOrNative ) libraries.add( it )
				}

				if( variant.buildType && it.name.contains( variant.buildType ) && libraryOrNative ) libraries.add( it )
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

			xml.append( configurationNode )
		}
	}

	private void createSdk()
	{
		String path = ""
		String file = ""

		switch( true )
		{
			case Os.isFamily( Os.FAMILY_WINDOWS ):
				path = "${ System.getProperty( "user.home" ) }/."
				break;

			case Os.isFamily( Os.FAMILY_UNIX ):
				path = "${ System.getProperty( "user.home" ) }/."
				break;

			case Os.isFamily( Os.FAMILY_MAC ):
				path = "${ System.getProperty( "user.home" ) }/"
				break;
		}

		switch( true )
		{
			case Os.isFamily( Os.FAMILY_WINDOWS ):
				file = "/config/options/jdk.table.xml"
				break;

			case Os.isFamily( Os.FAMILY_UNIX ):
				file = "/config/options/jdk.table.xml"
				break;

			case Os.isFamily( Os.FAMILY_MAC ):
				file = "/options/jdk.table.xml"
				break;
		}

		Sdk sdk = new Sdk( project )

		for( int i = 10; i <= 20; i++ )
		{
			File intellij = project.file( "${ path }IntelliJIdea${ i }${ file }" )

			if( intellij.exists( ) )
			{
				Node node = new XmlParser( ).parse( intellij )
				String sdkPath = project.file( sdk.path ).path.replaceAll( "\\\\" , "/" )

				NodeList list = node.component.jdk.findAll { it.homePath.@value[ 0 ] == sdkPath }

				if( list.size( ) )
				{
					list[ 0 ].name.@value == sdk.name
				}
				else
				{
					String jdk = sdkTemplate.replaceAll( "\\{name\\}" , sdk.name )
							.replaceAll( "\\{path\\}" , sdkPath )
							.replaceAll( "\\{fullVersion\\}" , "AIR SDK " + sdk.fullVersion )
							.replaceAll( "\\{version\\}" , sdk.version )

					Node jdkNode = new XmlParser( ).parseText( jdk )

					node.component[ 0 ].append( jdkNode )

					intellij.withWriter { writer -> XmlUtil.serialize( node , writer ) }
				}
			}
		}
	}
}