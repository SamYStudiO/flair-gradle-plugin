package flair.gradle.directoryWatcher.generated

import flair.gradle.directoryWatcher.IWatcherAction
import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.FlairProperties
import org.gradle.api.Project
import org.gradle.api.file.FileTree

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class GenerateRClass implements IWatcherAction
{
	public static String template

	@Override
	public void execute( Project project )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager

		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name )
		String packageName = extensionManager.getFlairProperty( FlairProperties.PACKAGE_NAME.name )

		if( !moduleName || !packageName || !template ) return

		File classFile = project.file( "${ moduleName }/src/main/generated/R.as" )

		if( !classFile.exists( ) ) return

		String classFileContent = template
		FileTree resources = project.fileTree( "${ moduleName }/src" )

		String bools = ""
		String colors = ""
		String dimens = ""
		String drawables = ""
		String integers = ""
		String objects = ""
		String raws = ""
		String sounds = ""
		String strings = ""
		String xmls = ""

		ArrayList<String> boolList = new ArrayList<String>( )
		ArrayList<String> colorList = new ArrayList<String>( )
		ArrayList<String> dimenList = new ArrayList<String>( )
		ArrayList<String> drawableList = new ArrayList<String>( )
		ArrayList<String> integerList = new ArrayList<String>( )
		ArrayList<String> objectList = new ArrayList<String>( )
		ArrayList<String> rawList = new ArrayList<String>( )
		ArrayList<String> soundList = new ArrayList<String>( )
		ArrayList<String> stringList = new ArrayList<String>( )
		ArrayList<String> xmlList = new ArrayList<String>( )

		Node node

		resources.each { file ->
			if( file.parentFile.parentFile.name == "resources" || file.parentFile.parentFile.parentFile.name == "resources" )
			{
				File parent = file.parentFile

				while( parent.parentFile.name != "resources" )
				{
					parent = parent.parentFile
				}

				String parentName = parent.name
				String flavor = parent.parentFile.parentFile.name
				String conditionalOpen = flavor == "main" ? "" : "CONFIG::${ flavor.toUpperCase( ) } "
				String conditionalClose = flavor == "main" ? "" : " }"
				String filename = file.name.split( "\\." )[ 0 ]
				String ext = file.name.split( "\\." )[ 1 ]

				if( parentName.indexOf( "xml" ) == 0 && !xmlList.contains( filename ) )
				{
					xmls += "\t${ conditionalOpen }public const ${ filename } : XML = getXml( \"${ filename }\" );${ conditionalClose }" + System.lineSeparator( )
					xmlList.add( filename )
				}
				else if( parentName.indexOf( "raw" ) == 0 )
				{
					if( ( ext == "mpeg" || ext == "mp3" ) && !soundList.contains( filename ) )
					{
						sounds += "\t${ conditionalOpen }public const ${ filename } : Sound = getSound( \"${ filename }\" );${ conditionalClose }" + System.lineSeparator( )
						soundList.add( filename )
					}
					else if( ext == "json" && !objectList.contains( filename ) )
					{
						objects += "\t${ conditionalOpen }public const ${ filename } : Object = getObject( \"${ filename }\" );${ conditionalClose }" + System.lineSeparator( )
						objectList.add( filename )
					}
					else if( !rawList.contains( filename ) )
					{
						raws += "\t${ conditionalOpen }public const ${ filename } : ByteArray = getByteArray( \"${ filename }\" );${ conditionalClose }" + System.lineSeparator( )
						rawList.add( filename )
					}
				}
				else if( parentName.indexOf( "values" ) == 0 )
				{
					node = new XmlParser( ).parse( file )
					node.string.each { string ->
						if( !stringList.contains( string.@name.toString( ) ) )
						{
							strings += "\t${ conditionalOpen }public const ${ string.@name } : String = getString( \"${ string.@name }\" );${ conditionalClose }" + System.lineSeparator( )
							stringList.add( string.@name.toString( ) )
						}
					}
					node.color.each { color ->
						if( !colorList.contains( color.@name.toString( ) ) )
						{
							colors += "\t${ conditionalOpen }public const ${ color.@name } : uint = getColor( \"${ color.@name }\" );${ conditionalClose }" + System.lineSeparator( )
							colorList.add( color.@name.toString( ) )
						}
					}
					node.bool.each { bool ->
						if( !boolList.contains( bool.@name.toString( ) ) )
						{
							bools += "\t${ conditionalOpen }public const ${ bool.@name } : Boolean = getBoolean( \"${ bool.@name }\" );${ conditionalClose }" + System.lineSeparator( )
							boolList.add( bool.@name.toString( ) )
						}
					}
					node.dimen.each { dimen ->
						if( !dimenList.contains( dimen.@name.toString( ) ) )
						{
							dimens += "\t${ conditionalOpen }public const ${ dimen.@name } : Number = getDimen( \"${ dimen.@name }\" );${ conditionalClose }" + System.lineSeparator( )
							dimenList.add( dimen.@name.toString( ) )
						}
					}
					node.integer.each { integer ->
						if( !integerList.contains( integer.@name.toString( ) ) )
						{
							integers += "\t${ conditionalOpen }public const ${ integer.@name } : int = getInteger( \"${ integer.@name }\" );${ conditionalClose }" + System.lineSeparator( )
							integerList.add( integer.@name.toString( ) )
						}
					}
				}
				else if( parentName.indexOf( "drawable" ) == 0 )
				{
					boolean isAtlas = ( ext != "xml" ) && new File( file.parentFile.path + File.separator + filename + ".xml" ).exists( )

					if( ext == "xml" )
					{
						node = new XmlParser( ).parse( file )

						node.SubTexture.each { texture ->
							if( !drawableList.contains( texture.@name.toString( ) ) )
							{
								drawables += "\t${ conditionalOpen }public const ${ texture.@name } : Texture = getDrawable( \"${ texture.@name }\" );${ conditionalClose }" + System.lineSeparator( )
								drawableList.add( texture.@name.toString( ) )
							}
						}
					}
					else if( !isAtlas && !drawableList.contains( filename ) )
					{
						drawables += "\t${ conditionalOpen }public const ${ filename } : Texture = getDrawable( \"${ filename }\" );${ conditionalClose }" + System.lineSeparator( )
						drawableList.add( filename )
					}
				}
			}
		}

		classFileContent = classFileContent.replaceAll( /}[^{}]*new R\( new Singleton\(\) \);/ , "}" + System.lineSeparator( ) + System.lineSeparator( ) + "import flair.resources.*;" + System.lineSeparator( ) + "import flash.media.Sound;" + System.lineSeparator( ) + "import flash.utils.ByteArray;" + System.lineSeparator( ) + "import starling.textures.Texture;" + System.lineSeparator( ) + System.lineSeparator( ) + "new R( new Singleton() );" )
		classFileContent = classFileContent.replaceAll( /class RBool\s*\{[^}]*}/ , "class RBool" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + bools + "}" )
		classFileContent = classFileContent.replaceAll( /class RColor\s*\{[^}]*}/ , "class RColor" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + colors + "}" )
		classFileContent = classFileContent.replaceAll( /class RDimen\s*\{[^}]*}/ , "class RDimen" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + dimens + "}" )
		classFileContent = classFileContent.replaceAll( /class RDrawable\s*\{[^}]*}/ , "class RDrawable" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + drawables + "}" )
		classFileContent = classFileContent.replaceAll( /class RInteger\s*\{[^}]*}/ , "class RInteger" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + integers + "}" )
		classFileContent = classFileContent.replaceAll( /class RObject\s*\{[^}]*}/ , "class RObject" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + objects + "}" )
		classFileContent = classFileContent.replaceAll( /class RRaw\s*\{[^}]*}/ , "class RRaw" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + raws + "}" )
		classFileContent = classFileContent.replaceAll( /class RSound\s*\{[^}]*}/ , "class RSound" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + sounds + "}" )
		classFileContent = classFileContent.replaceAll( /class RString\s*\{[^}]*}/ , "class RString" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + strings + "}" )
		classFileContent = classFileContent.replaceAll( /class RXml\s*\{[^}]*}/ , "class RXml" + System.lineSeparator( ) + "{" + System.lineSeparator( ) + xmls + "}" )

		classFile.write( classFileContent )
	}
}
