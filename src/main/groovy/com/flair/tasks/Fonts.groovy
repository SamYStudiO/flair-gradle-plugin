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
		group = "fonts"
		description = ""
	}

	@TaskAction
	public void generateFontClass()
	{
		String moduleName = project.flair.moduleName
		String appId = project.flair.appId
		String appIdPath = appId.replace( "." , "/" )

		FileTree tree = project.fileTree( "${ moduleName }/src/main/fonts" )

		String fontsClassContent = ""

		String fonts = ""

		tree.each { file ->
			String name = file.getName( )
			String fontFamily = getFontFamily( name )

			if( fonts.indexOf( fontFamily ) < 0 )
			{
				String upper = getUpperCaseFontFamily( getFontFamily( name ) )

				fontsClassContent = fontsClassContent.concat( System.lineSeparator( ) )
				fontsClassContent = fontsClassContent.concat( String.format( "\t\t/**%n\t\t *%n\t\t */%n\t\tpublic static const ${ upper } : String = \"${ fontFamily }\";%n" ) )
				fonts = fonts.concat( fontFamily )
			}

			fontsClassContent = fontsClassContent.concat( generateFont( name ) )
		}

		File f = project.file( "${ moduleName }/src/main/generated/Fonts.as" )
		String content = f.getText( )
		content = content.replaceAll( /class Fonts(\s|.)*function Fonts/ , String.format( "class Fonts%n\t{\t\t" + fontsClassContent + "%n\t\t/**%n\t\t * @private%n\t\t */%n\t\tpublic function Fonts" ) )
		f.write( content )
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

		return String.format( "\t\t[Embed(source=\"/${ filename }\",fontFamily=\"${ fontFamily }\",fontWeight=\"${ fontWeight }\",fontStyle=\"${ fontStyle }\",mimeType=\"application/x-font\",embedAsCFF=\"${ cff }\")]%n\t\tprotected static var ${ upper }_CLASS : Class;%n" )
	}
}
