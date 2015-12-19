package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 19/12/2015.
 */
public class ResourceLinkGenerator extends DefaultTask
{
	public ResourceLinkGenerator()
	{
		group = "resources"
		description = ""
	}

	@TaskAction
	public void generateResources()
	{
		String moduleName = project.flair.moduleName
		String appId = project.flair.appId

		if( appId.isEmpty( ) ) throw new IllegalArgumentException( String.format( "Missing appId property add%nflair {%n	appId = \"myAppid\"%n}%nto your build.gradle file." ) )

		String s = appId.replace( "." , "/" )

		File classFile = project.file( "${ moduleName }/src/main/actionscript/${ s }/resources/R.as" )
		String classFileContent = classFile.getText( )
		FileTree resources = project.fileTree( "${ moduleName }/src/main/resources" )

		String bools = ""
		String byteArrays = ""
		String colors = ""
		String dimens = ""
		String drawables = ""
		String integers = ""
		String objects = ""
		String sounds = ""
		String strings = ""
		String xmls = ""

		ArrayList<String> boolList = new ArrayList<String>( )
		ArrayList<String> byteArrayList = new ArrayList<String>( )
		ArrayList<String> colorList = new ArrayList<String>( )
		ArrayList<String> dimenList = new ArrayList<String>( )
		ArrayList<String> drawableList = new ArrayList<String>( )
		ArrayList<String> integerList = new ArrayList<String>( )
		ArrayList<String> objectList = new ArrayList<String>( )
		ArrayList<String> soundList = new ArrayList<String>( )
		ArrayList<String> stringList = new ArrayList<String>( )
		ArrayList<String> xmlList = new ArrayList<String>( )

		Node node

		resources.each { file ->
			if( file.getParentFile( ).getName( ) != "resources" )
			{
				File parent = file.getParentFile( )

				while( parent.getParentFile( ).getName( ) != "resources" )
				{
					parent = parent.getParentFile( )
				}

				String parentName = parent.getName( )
				String filename = file.getName( ).split( "\\." )[ 0 ]
				String ext = file.getName( ).split( "\\." )[ 1 ]

				if( parentName.indexOf( "xml" ) == 0 && !xmlList.contains( filename ) )
				{
					xmls += "\tpublic const ${ filename.toUpperCase( ) } : String = \"${ filename }\";" + System.lineSeparator( )
					xmlList.add( filename )
				}
				else if( parentName.indexOf( "raw" ) == 0 )
				{
					if( ( ext == "mpeg" || ext == "mp3" ) && !soundList.contains( filename ) )
					{
						sounds += "\tpublic const ${ filename.toUpperCase( ) } : String = \"${ filename }\";" + System.lineSeparator( )
						soundList.add( filename )
					}
					else if( ext == "json" && !objectList.contains( filename ) )
					{
						objects += "\tpublic const ${ filename.toUpperCase( ) } : String = \"${ filename }\";" + System.lineSeparator( )
						objectList.add( filename )
					}
					else if( !byteArrayList.contains( filename ) )
					{
						byteArrays += "\tpublic const ${ filename.toUpperCase( ) } : String = \"${ filename }\";" + System.lineSeparator( )
						byteArrayList.add( filename )
					}
				}
				else if( parentName.indexOf( "values" ) == 0 )
				{
					node = new XmlParser( ).parse( file )
					node.string.each { string ->
						if( !stringList.contains( string.@name.toString( ) ) )
						{
							strings += "\tpublic const ${ string.@name.toUpperCase( ) } : String = \"${ string.@name }\";" + System.lineSeparator( )
							stringList.add( string.@name.toString( ) )
						}
					}
					node.color.each { color ->
						if( !colorList.contains( color.@name.toString( ) ) )
						{
							colors += "\tpublic const ${ color.@name.toUpperCase( ) } : String = \"${ color.@name }\";" + System.lineSeparator( )
							colorList.add( color.@name.toString( ) )
						}
					}
					node.bool.each { bool ->
						if( !boolList.contains( bool.@name.toString( ) ) )
						{
							bools += "\tpublic const ${ bool.@name.toUpperCase( ) } : String = \"${ bool.@name }\";" + System.lineSeparator( )
							boolList.add( bool.@name.toString( ) )
						}
					}
					node.dimen.each { dimen ->
						if( !dimenList.contains( dimen.@name.toString( ) ) )
						{
							dimens += "\tpublic const ${ dimen.@name.toUpperCase( ) } : String = \"${ dimen.@name }\";" + System.lineSeparator( )
							dimenList.add( dimen.@name.toString( ) )
						}
					}
					node.integer.each { integer ->
						if( !integerList.contains( integer.@name.toString( ) ) )
						{
							integers += "\tpublic const ${ integer.@name.toUpperCase( ) } : String = \"${ integer.@name }\";" + System.lineSeparator( )
							integerList.add( integer.@name.toString( ) )
						}
					}
				}
				else if( parentName.indexOf( "drawable" ) == 0 )
				{
					Boolean isAtlas = ( ext != "xml" ) && new File( file.getParentFile( ).getPath( ) + File.separator + filename + ".xml" ).exists( )

					if( ext == "xml" )
					{
						node = new XmlParser( ).parse( file )

						node.SubTexture.each { texture ->
							if( !drawableList.contains( texture.@name.toString( ) ) )
							{
								drawables += "\tpublic const ${ texture.@name.toUpperCase( ) } : String = \"${ texture.@name }\";" + System.lineSeparator( )
								drawableList.add( texture.@name.toString( ) )
							}
						}
					}
					else if( !isAtlas && !drawableList.contains( filename ) )
					{
						drawables += "\tpublic const ${ filename.toUpperCase( ) } : String = \"${ filename }\";" + System.lineSeparator( )
						drawableList.add( filename )
					}
				}
			}
		}

		classFileContent = classFileContent.replaceAll( /class Bool\s*\{[^}]*}/ , "class Bool" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + bools + "}" )
		classFileContent = classFileContent.replaceAll( /class ByteArray\s*\{[^}]*}/ , "class ByteArray" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + byteArrays + "}" )
		classFileContent = classFileContent.replaceAll( /class Color\s*\{[^}]*}/ , "class Color" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + colors + "}" )
		classFileContent = classFileContent.replaceAll( /class Dimen\s*\{[^}]*}/ , "class Dimen" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + dimens + "}" )
		classFileContent = classFileContent.replaceAll( /class Drawable\s*\{[^}]*}/ , "class Drawable" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + drawables + "}" )
		classFileContent = classFileContent.replaceAll( /class Integer\s*\{[^}]*}/ , "class Integer" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + integers + "}" )
		classFileContent = classFileContent.replaceAll( /class Object\s*\{[^}]*}/ , "class Object" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + objects + "}" )
		classFileContent = classFileContent.replaceAll( /class Sound\s*\{[^}]*}/ , "class Sound" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + sounds + "}" )
		classFileContent = classFileContent.replaceAll( /class String\s*\{[^}]*}/ , "class String" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + strings + "}" )
		classFileContent = classFileContent.replaceAll( /class Xml\s*\{[^}]*}/ , "class Xml" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + xmls + "}" )

		classFile.write( classFileContent )
	}
}