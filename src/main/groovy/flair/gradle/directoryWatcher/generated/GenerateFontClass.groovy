package flair.gradle.directoryWatcher.generated

import flair.gradle.directoryWatcher.IWatcherAction
import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.Properties
import org.gradle.api.Project
import org.gradle.api.file.FileTree

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class GenerateFontClass implements IWatcherAction
{
	public static String template

	@Override
	public void execute( Project project )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager

		String moduleName = extensionManager.getFlairProperty( Properties.MODULE_NAME.name )

		if( !moduleName || !template ) return

		FileTree tree = project.fileTree( "${ moduleName }/src" )

		String fontsClassContent = ""

		String fonts = ""

		tree.each { file ->

			File parent = file.parentFile

			if( parent.isDirectory( ) && parent.name == "fonts" )
			{
				String name = file.getName( )
				String fontFamily = getFontFamily( name )
				String flavor = parent.parentFile.name
				String conditional = flavor == "main" ? "" : "CONFIG::${ flavor.toUpperCase( ) } "
				String conditionalOpen = flavor == "main" ? "" : "CONFIG::${ flavor.toUpperCase( ) }{%n\t\t"
				String conditionalClose = flavor == "main" ? "" : "%n\t\t}"
				String upper = getUpperCaseFontFamily( getFontFamily( name ) )

				if( fonts.indexOf( fontFamily ) < 0 )
				{

					fontsClassContent = fontsClassContent.concat( System.lineSeparator( ) )
					fontsClassContent = fontsClassContent.concat( String.format( "\t\t/**%n\t\t *%n\t\t */%n\t\t${ conditional }public static const ${ upper } : String = \"${ fontFamily }\";%n" ) )
					fonts = fonts.concat( fontFamily )
				}

				//TODO remove conditional if same font family is added to main
				//else if( conditional == "" ) fontsClassContent.replace( "CONFIG::?* public static const ${ upper }" , "public static const ${ upper }" )

				fontsClassContent = fontsClassContent.concat( generateFont( name , conditionalOpen , conditionalClose ) )
			}
		}

		File f = project.file( "${ moduleName }/src/main/generated/Fonts.as" )

		if( !f.exists( ) ) return

		String content = template

		if( fontsClassContent != "" || content.indexOf( "Embed" ) > 0 )
		{
			content = content.replaceAll( /class Fonts(\s|.)*function Fonts/ , String.format( "class Fonts%n\t{\t\t" + fontsClassContent + "%n\t\t/**%n\t\t * @private%n\t\t */%n\t\tpublic function Fonts" ) )
			f.write( content )
		}
	}

	private String trimExt( String filename )
	{
		return filename.split( "\\." ).first( )
	}

	private String getFontFamily( String filename )
	{
		return trimExt( filename ).replaceAll( /(?i)bold/ , "" ).replaceAll( /(?i)italic/ , "" ).replaceAll( /(?i)cff/ , "" )
	}

	private String getUpperCaseFontFamily( String filename )
	{
		String upName = filename.replaceAll( /[A-Z]/ , /_$0/ ).toUpperCase( )

		if( upName.charAt( 0 ).toString( ) == "_" ) upName = upName.substring( 1 )

		return upName
	}

	private String generateFont( String filename , String conditionalOpen , String conditionalClose )
	{
		String fontFamily = getFontFamily( filename )
		String fontWeight = filename.toLowerCase( ).indexOf( "bold" ) >= 0 ? "bold" : "normal"
		String fontStyle = filename.toLowerCase( ).indexOf( "italic" ) >= 0 ? "italic" : "normal"
		String cff = filename.toLowerCase( ).indexOf( "cff" ) >= 0 ? "true" : "false"
		String upper = getUpperCaseFontFamily( trimExt( filename ) )

		return String.format( "\t\t${ conditionalOpen }[Embed(source=\"/${ filename }\",fontFamily=\"${ fontFamily }\",fontWeight=\"${ fontWeight }\",fontStyle=\"${ fontStyle }\",mimeType=\"application/x-font\",embedAsCFF=\"${ cff }\")]%n\t\tprivate static var ${ upper }_CLASS : Class;${ conditionalClose }%n" )
	}
}
