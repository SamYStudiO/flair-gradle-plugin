package flair.gradle.tasks

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class GenerateResourcesClass extends AbstractTask
{
	private final static String TEMPLATE = "package\n" + "{\n" + "\t/**\n" + "\t * DO NOT edit this class, this is auto generated from your resources directories.\n" + "\t * This class try to mimic android R class but will return resource instances instead of resource ids.\n" + "\t * For example :\n" + "\t * var image : Image = new Image( R.drawable.drawable_id );\n" + "\t * var text : String = R.string.string_id;\n" + "\t * var soundChannel : SoundChannel = R.sound.sound_id.play();\n" + "\t */\n" + "\tpublic final class R\n" + "\t{\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var bool : RBool;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var color : RColor;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var dimen : RDimen;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var drawable : RDrawable;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var integer : RInteger;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var object : RObject;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var raw : RRaw;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var sound : RSound;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var string : RString;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic static var xml : RXml;\n" + "\n" + "\t\t/**\n" + "\t\t *\n" + "\t\t */\n" + "\t\tpublic function R( singleton : Singleton )\n" + "\t\t{\n" + "\t\t\tbool = new RBool();\n" + "\t\t\tcolor = new RColor();\n" + "\t\t\tdimen = new RDimen();\n" + "\t\t\tdrawable = new RDrawable();\n" + "\t\t\tinteger = new RInteger();\n" + "\t\t\tobject = new RObject();\n" + "\t\t\traw = new RRaw();\n" + "\t\t\tsound = new RSound();\n" + "\t\t\tstring = new RString();\n" + "\t\t\txml = new RXml();\n" + "\t\t}\n" + "\t}\n" + "}\n" + "\n" + "import flair.resources.getString;\n" + "\n" + "new R( new Singleton() );\n" + "\n" + "class Singleton\n" + "{\n" + "\n" + "}\n" + "\n" + "class RBool\n" + "{\n" + "\n" + "}\n" + "\n" + "class RColor\n" + "{\n" + "\n" + "}\n" + "\n" + "class RDimen\n" + "{\n" + "\n" + "}\n" + "\n" + "class RDrawable\n" + "{\n" + "\n" + "}\n" + "\n" + "class RInteger\n" + "{\n" + "\n" + "}\n" + "\n" + "class RObject\n" + "{\n" + "\n" + "}\n" + "\n" + "class RRaw\n" + "{\n" + "\n" + "}\n" + "\n" + "class RSound\n" + "{\n" + "\n" + "}\n" + "\n" + "class RString\n" + "{\n" + "\tpublic const first_screen : String = getString( \"first_screen\" );\n" + "\tpublic const hello : String = getString( \"hello\" );\n" + "\tpublic const world : String = getString( \"world\" );\n" + "}\n" + "\n" + "class RXml\n" + "{\n" + "\n" + "}"

	@InputFiles
	def Set<File> inputFiles

	@OutputFile
	def File outputFile

	public GenerateResourcesClass()
	{
		group = TaskGroup.GENERATED.name
		description = "Generates main/generated/R.as from res directories"
	}

	@TaskAction
	public void generate()
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager

		String moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )
		String packageName = extensionManager.getFlairProperty( FlairProperty.PACKAGE_NAME )

		if( !moduleName || !packageName ) return

		File classFile = project.file( "${ moduleName }/src/main/generated/R.as" )
		classFile.createNewFile(  )

		String classFileContent = TEMPLATE
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
			if( file.parentFile.parentFile.name == "res" || file.parentFile.parentFile.parentFile.name == "res" )
			{
				File parent = file.parentFile

				while( parent.parentFile.name != "res" )
				{
					parent = parent.parentFile
				}

				String parentName = parent.name
				String filename = file.name.split( "\\." )[ 0 ]
				String varName = filename.replaceAll( "-" , "_" )
				String ext = file.name.split( "\\." )[ 1 ]

				if( parentName.indexOf( "xml" ) == 0 && !xmlList.contains( filename ) )
				{
					xmls += "\tpublic const ${ varName } : XML = getXml( \"${ filename }\" );" + System.lineSeparator( )
					xmlList.add( filename )
				}
				else if( parentName.indexOf( "raw" ) == 0 )
				{
					if( ( ext == "mpeg" || ext == "mp3" ) && !soundList.contains( filename ) )
					{
						sounds += "\tpublic const ${ varName } : Sound = getSound( \"${ filename }\" );" + System.lineSeparator( )
						soundList.add( filename )
					}
					else if( ext == "json" && !objectList.contains( filename ) )
					{
						objects += "\tpublic const ${ varName } : Object = getObject( \"${ filename }\" );" + System.lineSeparator( )
						objectList.add( filename )
					}
					else if( !rawList.contains( filename ) )
					{
						raws += "\tpublic const ${ varName } : ByteArray = getByteArray( \"${ filename }\" );" + System.lineSeparator( )
						rawList.add( filename )
					}
				}
				else if( parentName.indexOf( "values" ) == 0 )
				{
					node = new XmlParser( ).parse( file )
					node.string.each { string ->

						varName = string.@name.toString( ).replaceAll( "-" , "_" )

						if( !stringList.contains( string.@name.toString( ) ) )
						{
							strings += "\tpublic const ${ varName } : String = getString( \"${ string.@name }\" );" + System.lineSeparator( )
							stringList.add( string.@name.toString( ) )
						}
					}
					node.color.each { color ->

						varName = color.@name.toString( ).replaceAll( "-" , "_" )

						if( !colorList.contains( color.@name.toString( ) ) )
						{
							colors += "\tpublic const ${ varName } : uint = getColor( \"${ color.@name }\" );" + System.lineSeparator( )
							colorList.add( color.@name.toString( ) )
						}
					}
					node.bool.each { bool ->

						varName = bool.@name.toString( ).replaceAll( "-" , "_" )

						if( !boolList.contains( bool.@name.toString( ) ) )
						{
							bools += "\tpublic const ${ varName } : Boolean = getBoolean( \"${ bool.@name }\" );" + System.lineSeparator( )
							boolList.add( bool.@name.toString( ) )
						}
					}
					node.dimen.each { dimen ->

						varName = dimen.@name.toString( ).replaceAll( "-" , "_" )

						if( !dimenList.contains( dimen.@name.toString( ) ) )
						{
							dimens += "\tpublic const ${ varName } : Number = getDimen( \"${ dimen.@name }\" );" + System.lineSeparator( )
							dimenList.add( dimen.@name.toString( ) )
						}
					}
					node.integer.each { integer ->

						varName = integer.@name.toString( ).replaceAll( "-" , "_" )

						if( !integerList.contains( integer.@name.toString( ) ) )
						{
							integers += "\tpublic const ${ varName } : int = getInteger( \"${ integer.@name }\" );" + System.lineSeparator( )
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

							varName = texture.@name.toString( ).replaceAll( "-" , "_" )

							if( !drawableList.contains( texture.@name.toString( ) ) )
							{
								drawables += "\tpublic const ${ varName } : Texture = getDrawable( \"${ texture.@name }\" );" + System.lineSeparator( )
								drawableList.add( texture.@name.toString( ) )
							}
						}
					}
					else if( !isAtlas && !drawableList.contains( filename ) )
					{
						drawables += "\tpublic const ${ varName } : Texture = getDrawable( \"${ filename }\" );" + System.lineSeparator( )
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

	public void findInputAndOutputFiles()
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager
		String moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )

		inputFiles = new ArrayList<File>( )

		List<String> list = new ArrayList<String>( )

		extensionManager.allActivePlatformVariants.each {

			it.directories.each { directory ->

				if( list.indexOf( directory ) < 0 ) list.add( directory )
			}
		}

		list.each {

			inputFiles.add( project.file( "${ moduleName }/src/${ it }/res" ) )
		}

		outputFile = project.file( "${ moduleName }/src/main/generated/R.as" )
	}
}
