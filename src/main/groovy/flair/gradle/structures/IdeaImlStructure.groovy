package flair.gradle.structures

import flair.gradle.dependencies.Sdk
import flair.gradle.extensions.FlairProperties
import flair.gradle.extensions.IExtensionManager
import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import groovy.xml.XmlUtil
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class IdeaImlStructure implements IStructure
{
	private Project project

	private String moduleName

	private IExtensionManager extensionManager

	private String configurationTemplate

	@Override
	public void create( Project project , File source )
	{
		this.project = project

		extensionManager = project.flair as IExtensionManager
		moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name )
		configurationTemplate = project.file( "${ source.path }/idea/configuration_template.xml" ).text

		File output = project.file( "${ moduleName }/${ moduleName }.iml" )
		File file = !output.exists( ) ? project.file( "${ source.path }/idea/template.iml" ) : output

		Node xml = new XmlParser( ).parse( file )
		Node flexBuildConfigurationManager = xml.children( ).find { it.@name == "FlexBuildConfigurationManager" } as Node
		Node newModuleRootManager = xml.children( ).find { it.@name == "NewModuleRootManager" } as Node
		Node content = newModuleRootManager.content[ 0 ] as Node

		if( !content ) content = new Node( newModuleRootManager , "content" )

		createSourceFolders( content )
		createExcludeFolders( content )
		//createLibraries( newModuleRootManager )
		createConfigurations( flexBuildConfigurationManager.configurations[ 0 ] as Node )

		output.withWriter { writer -> XmlUtil.serialize( xml , writer ) }
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

	private void createLibraries( Node xml )
	{
		xml.children( ).findAll { it.name( ) == "orderEntry" && it.@type == "library" }.each { it.parent( ).remove( it ) }

		List<String> list = new ArrayList<String>( )

		new Node( xml , "orderEntry" , [ exported: "" , name: "as3_libs" , level: "project" ] )

		project.configurations.findAll {
			it.name.toLowerCase( ).contains( "librarycompile" ) || it.name.toLowerCase( ).contains( "nativecompile" )
		}.each {

			it.files.each { file ->

				if( file.exists( ) )
				{
					String path = file.isDirectory( ) ? file.path : file.parentFile.path

					if( !list.contains( path ) ) new Node( xml , "orderEntry" , [ exported: "" , name: project.file( path ).name , level: "project" ] )

					list.add( path )
				}
			}
		}
	}

	private void createConfigurations( Node xml )
	{
		xml.children( ).findAll { it.@name == null || it.@name.startsWith( "flair_" ) }.each { it.parent( ).remove( it ) }

		extensionManager.allActivePlatformVariants.each {

			String platform = ""

			switch( it.platform )
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

			println( ">>>" + buildPathFromModule( project.buildDir.path ) )

			configuration = configuration.replaceAll( "\\{configurationName\\}" , "flair_" + it.getNameWithType( Variant.NamingTypes.UNDERSCORE ) )
					.replaceAll( "\\{platform\\}" , platform )
					.replaceAll( "\\{mainClass\\}" , extensionManager.getFlairProperty( it , FlairProperties.COMPILE_MAIN_CLASS.name ) as String )
					.replaceAll( "\\{outputSwf\\}" , it.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + ".swf" )
					.replaceAll( "\\{buildDir\\}" , buildPathFromModule( project.buildDir.path , true ) )
					.replaceAll( "\\{target\\}" , it.platform == Platforms.DESKTOP ? "Desktop" : "Mobile" )
					.replaceAll( "\\{sdkName\\}" , new Sdk( project ).name )
					.replaceAll( "\\{debug\\}" , extensionManager.getFlairProperty( it , FlairProperties.DEBUG.name ) ? "true" : "false" )
					.replaceAll( "\\{constants\\}" , "" )

			Node conf = new XmlParser( ).parseText( configuration )

			Node platformNode = null

			switch( it.platform )
			{
				case Platforms.IOS:
					platformNode = conf."packaging-ios"[ 0 ] as Node
					break

				case Platforms.ANDROID:
					platformNode = conf."packaging-android"[ 0 ] as Node
					break

				case Platforms.DESKTOP:
					platformNode = conf."packaging-air-desktop"[ 0 ] as Node
					break
			}

			platformNode.@"enabled" = "true"
			platformNode.@"use-generated-descriptor" = "false"
			platformNode.@"custom-descriptor-path" = buildPathFromModule( project.file( "${ project.buildDir.path }/${ it.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }/app_descriptor.xml" ).path )
			platformNode.@"package-file-name" = "${ it.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }_${ extensionManager.getFlairProperty( it , FlairProperties.APP_VERSION.name ) } }"

			if( platformNode."files-to-package"[ 0 ] == null ) new Node( platformNode , "files-to-package" )

			Node toPackage = platformNode."files-to-package"[ 0 ]

			toPackage.children( ).each { it.parent( ).remove( it ) }


			project.file( "${ project.buildDir.path }/${ it.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }/package" ).listFiles( ).each {

				Node node = new Node( toPackage , "FilePathAndPathInPackage" )

				node.@"file-path" = buildPathFromModule( it.path )
				node.@"path-in-package" = it.name
			}

			xml.append( conf )
		}
	}
}