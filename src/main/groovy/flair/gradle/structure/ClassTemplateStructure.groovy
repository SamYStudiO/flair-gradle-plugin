package flair.gradle.structure

import flair.gradle.extensions.configuration.PropertyManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ClassTemplateStructure implements IStructure
{
	@Override
	public void create( Project project )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( !moduleName ) return

		String packageName = PropertyManager.getProperty( project , "packageName" )

		if( !packageName || project.fileTree( "${ moduleName }/src/main/actionscript" ).size( ) > 0 ) return

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).toExternalForm( ) )
			//from project.fileTree( new File( getClass( ).getResource( "scaffold/src" ).toURI() ).absolutePath )
			into project.getRootDir( )

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		String s = packageName.replace( "." , "/" )

		project.copy {
			from "scaffold/_packageName_"
			into "${ moduleName }/src/main/actionscript/${ s }"
		}

		project.fileTree( "${ moduleName }/src/main/actionscript/${ s }" ).each { file ->

			file.write( file.getText( ).replace( "_packageName_" , packageName ) )
		}

		project.file( "scaffold" ).deleteDir( )
	}
}