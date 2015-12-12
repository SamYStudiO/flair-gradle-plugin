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
	public void generateFonts()
	{
		String moduleName = project.flair.moduleName
		String appId = project.flair.appId
		String appIdPath = appId.replace( "." , "/" )

		FileTree tree = project.fileTree( "${ moduleName }/src/main/resources/fonts" )

		String fontsClassContent = String.format( "package ${ appId }.theme%n{%n\t/**%n\t * @author SamYStudiO ( contact@samystudio.net )%n\t */%n\tpublic final class Fonts%n\t{%n" )

		String fonts = ""

		tree.each { file ->
			String name = file.getName( )
			String fontFamily = getFontFamily( name )

			if( name.toLowerCase( ).indexOf( ".otf" ) >= 0 || name.toLowerCase( ).indexOf( ".ttf" ) >= 0 )
			{
				if( fonts.indexOf( fontFamily ) < 0 )
				{
					String upper = getUpperCaseFontFamily( getFontFamily( name ) )

					if( !fonts.isEmpty( ) ) fontsClassContent = fontsClassContent.concat( System.lineSeparator( ) )
					fontsClassContent = fontsClassContent.concat( String.format( "\t\t/**%n\t\t *%n\t\t */%n\t\tpublic static const ${ upper } : String = \"${ fontFamily }\";%n" ) )
					fonts = fonts.concat( fontFamily )
				}

				fontsClassContent = fontsClassContent.concat( generateFont( name ) )
			}
		}

		fontsClassContent = fontsClassContent.concat( String.format( "%n\t\t/**%n\t\t * @private%n\t\t */%n\t\tpublic function Fonts()%n\t\t{%n\t\t\tthrow new Error( this + \" cannot be instantiated\" );%n\t\t}%n" + "\t}%n}" ) )

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

		return String.format( "\t\t[Embed(source=\"/${ filename }\",fontFamily=\"${ fontFamily }\",fontWeight=\"${ fontWeight }\",fontStyle=\"${ fontStyle }\",mimeType=\"application/x-font\",embedAsCFF=\"${ cff }\")]%n\t\tprotected static var ${ upper }_CLASS : Class;%n" )
	}
}
