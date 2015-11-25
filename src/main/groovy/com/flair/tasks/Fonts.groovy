package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO on 25/11/2015.
 */
class Fonts extends DefaultTask
{
	public Fonts()
	{
		group = "scaffold"
		description = ""
	}

	@TaskAction
	public void generateFonts()
	{
		String moduleName = project.flair.moduleName
		String appId = project.flair.appId
		String appIdPath = appId.replace( "." , File.separator )

		FileTree tree = project.fileTree( "${ moduleName }/src/main/resources/fonts" )

		String fontsClassContent = "package ${ appId }.theme\r\n{\r\n\t/**\r\n\t * @author SamYStudiO ( contact@samystudio.net )\r\n\t */\r\n\tpublic final class Fonts\r\n\t{\r\n"

		String fonts = ""

		tree.each { file ->
			String name = file.getName( )
			String fontFamily = getFontFamily( name )

			if( name.toLowerCase( ).indexOf( ".otf" ) >= 0 || name.toLowerCase( ).indexOf( ".ttf" ) >= 0 )
			{
				if( fonts.indexOf( fontFamily ) < 0 )
				{
					String upper = getUpperCaseFontFamily( getFontFamily( name ) )

					if( !fonts.isEmpty( ) ) fontsClassContent = fontsClassContent.concat( "\r\n" )
					fontsClassContent = fontsClassContent.concat( "\t\t/**\r\n\t\t *\r\n\t\t */\r\n\t\tpublic static const ${ upper } : String = \"${ fontFamily }\";\r\n" )
					fonts = fonts.concat( fontFamily )
				}

				fontsClassContent = fontsClassContent.concat( generateFont( name ) )
			}
		}

		fontsClassContent = fontsClassContent.concat( "\r\n\t\t/**\r\n\t\t * @private\r\n\t\t */\r\n\t\tpublic function Fonts()\r\n\t\t{\r\n\t\t\tthrow new Error( this + \" cannot be instantiated\" );\r\n\t\t}\r\n" + "\t}\r\n}" )

		File f = project.file( "${ moduleName }/src/main/actionscript/${ appIdPath }/theme/Fonts.as" )
		f.write( fontsClassContent )
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
		String upname = filename.replaceAll( /[A-Z]/ , /_$0/ ).toUpperCase( )

		if( upname.charAt( 0 ).toString( ) == "_" ) upname = upname.substring( 1 )

		return upname
	}

	protected String generateFont( String filename )
	{
		String fontFamily = getFontFamily( filename )
		String fontWeight = filename.toLowerCase( ).indexOf( "bold" ) >= 0 ? "bold" : "normal"
		String fontStyle = filename.toLowerCase( ).indexOf( "italic" ) >= 0 ? "italic" : "normal"
		String cff = filename.toLowerCase( ).indexOf( "cff" ) >= 0 ? "true" : "false"
		String upper = getUpperCaseFontFamily( trimExt( filename ) )

		return "\t\t[Embed(source=\"/${ filename }\",fontFamily=\"${ fontFamily }\",fontWeight=\"${ fontWeight }\",fontStyle=\"${ fontStyle }\",mimeType=\"application/x-font\",embedAsCFF=\"${ cff }\")]\r\n\t\tprotected static var ${ upper }_CLASS : Class;\r\n"
	}
}
