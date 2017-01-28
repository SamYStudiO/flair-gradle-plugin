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
class GenerateFontsClass extends AbstractTask
{
	private final static String TEMPLATE = "package\n" + "{\n" + "\t/**\n" + "\t * DO NOT edit this class, this is auto generated from your fonts directories.\n" + "\t * To make it work properly you need to add \"Bold/Italic/Cff\" explicitly in your font file name\n" + "\t * (for example arialBold.ttf, arialBoldItalicCff.ttf, verdanaCff.ttf),\n" + "\t * for better readability and class property name generation use camelCase with your font file names.\n" + "\t */\n" + "\tpublic final class Fonts\n" + "\t{\n" + "\t\tpublic function Fonts()\n" + "\t\t{\n" + "\t\t\tthrow new Error( this + \" cannot be instantiated\" );\n" + "\t\t}\n" + "\t}\n" + "}"

	@InputFiles
	Set<File> inputFiles

	@OutputFile
	File outputFile

	GenerateFontsClass()
	{
		group = TaskGroup.GENERATED.name
		description = "Generates main/generated/Fonts.as from fonts directories"
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void generate()
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager

		String moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )

		if( !moduleName ) return

		FileTree tree = project.fileTree( "${ moduleName }/src" )

		String fontsClassContent = ""

		String fonts = ""

		tree.each { file ->

			File parent = file.parentFile

			if( parent.isDirectory( ) && parent.name == "fonts" )
			{
				String name = file.getName( )
				String fontFamily = getFontFamily( name )
				String upper = getUpperCaseFontFamily( getFontFamily( name ) )

				if( fonts.indexOf( fontFamily ) < 0 )
				{
					fontsClassContent = fontsClassContent.concat( System.lineSeparator( ) )
					fontsClassContent = fontsClassContent.concat( String.format( "\t\tpublic static const ${ upper } : String = \"${ fontFamily }\";%n" ) )
					fonts = fonts.concat( fontFamily )
				}

				fontsClassContent = fontsClassContent.concat( generateFont( name ) )
			}
		}

		File f = project.file( "${ moduleName }/src/main/generated/Fonts.as" )
		f.createNewFile(  );

		String content = TEMPLATE

		if( fontsClassContent != "" || content.indexOf( "Embed" ) > 0 )
		{
			content = content.replaceAll( /class Fonts(\s|.)*function Fonts/ , String.format( "class Fonts%n\t{\t\t" + fontsClassContent + "%n\t\tpublic function Fonts" ) )
			f.write( content )
		}
	}

	protected String trimExt( String filename )
	{
		return filename.split( "\\." ).first( )
	}

	protected String getFontFamily( String filename )
	{
		return trimExt( filename ).replaceAll( /(?i)bold/ , "" ).replaceAll( /(?i)italic/ , "" ).replaceAll( /(?i)cff/ , "" )
	}

	protected String getUpperCaseFontFamily( String filename )
	{
		String upName = filename.replaceAll( /[A-Z]/ , /_$0/ ).toUpperCase( )

		if( upName.charAt( 0 ).toString( ) == "_" ) upName = upName.substring( 1 )

		return upName
	}

	protected String generateFont( String filename )
	{
		String fontFamily = getFontFamily( filename )
		String fontWeight = filename.toLowerCase( ).indexOf( "bold" ) >= 0 ? "bold" : "normal"
		String fontStyle = filename.toLowerCase( ).indexOf( "italic" ) >= 0 ? "italic" : "normal"
		String cff = filename.toLowerCase( ).indexOf( "cff" ) >= 0 ? "true" : "false"
		String upper = getUpperCaseFontFamily( trimExt( filename ) )

		return String.format( "\t\t[Embed(source=\"/${ filename }\",fontFamily=\"${ fontFamily }\",fontWeight=\"${ fontWeight }\",fontStyle=\"${ fontStyle }\",mimeType=\"application/x-font\",embedAsCFF=\"${ cff }\")]%n\t\tprivate static var ${ upper }_CLASS : Class;%n" )
	}

	void findInputAndOutputFiles()
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

			inputFiles.add( project.file( "${ moduleName }/src/${ it }/fonts" ) )
		}

		outputFile = project.file( "${ moduleName }/src/main/generated/Fonts.as" )
	}
}
