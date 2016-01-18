package flair.gradle.structure

import flair.gradle.extensions.configuration.PropertyManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class DesktopStructure implements IStructure
{
	@Override
	public void create( Project project )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( !moduleName ) return

		project.copy {
			from project.zipTree( getClass( ).getProtectionDomain( ).getCodeSource( ).getLocation( ).toExternalForm( ) )
			//from project.fileTree( new File( getClass( ).getResource( "scaffold/src" ).toURI() ).absolutePath )
			into project.getRootDir( )

			include "scaffold/**"
			exclude "**/.gitkeep"
		}

		if( project.fileTree( "${ moduleName }/src/desktop" ).size( ) > 0 ) return

		project.copy {
			from "scaffold/desktop"
			into "${ moduleName }/src/desktop"
		}

		project.file( "scaffold" ).deleteDir( )
	}
}
