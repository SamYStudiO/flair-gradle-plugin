package flair.gradle.structure

import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.variants.Platform
import flair.gradle.plugins.PluginManager
import flair.gradle.variants.VariantManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class StructureManager
{
	public static void updateStructure( Project project )
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

		createMainStructure( project )
		createPlatformStructure( project , Platform.IOS )
		createPlatformStructure( project , Platform.ANDROID )
		createPlatformStructure( project , Platform.DESKTOP )
		createSourceTemplate( project )
		updateVariantDirectories( project )

		project.file( "scaffold" ).deleteDir( )
	}

	private static void createMainStructure( Project project )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( project.fileTree( "${ moduleName }/src/main" ).size( ) > 0 ) return

		project.copy {
			from "scaffold/src/main"
			into "${ moduleName }/src/main"
		}
	}

	private static void createPlatformStructure( Project project , Platform platform )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		if( !PluginManager.hasPlatformPlugin( project , platform ) || project.fileTree( "${ moduleName }/src/${ platform.name.toLowerCase( ) }" ).size( ) > 0 ) return

		project.copy {
			from "scaffold/${ platform.name.toLowerCase( ) }"
			into "${ moduleName }/src/${ platform.name.toLowerCase( ) }"
		}
	}

	private static void createSourceTemplate( Project project )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )
		String packageName = PropertyManager.getProperty( project , "packageName" )

		if( !packageName || project.fileTree( "${ moduleName }/src/main/actionscript" ).size( ) > 0 ) return

		String s = packageName.replace( "." , "/" )

		project.copy {
			from "scaffold/_packageName_"
			into "${ moduleName }/src/main/actionscript/${ s }"
		}

		project.fileTree( "${ moduleName }/src/main/actionscript/${ s }" ).each { file ->

			file.write( file.getText( ).replace( "_packageName_" , packageName ) )
		}
	}

	private static void updateVariantDirectories( Project project )
	{
		boolean autoGenerateVariantDirectories = PropertyManager.getProperty( project , "autoGenerateVariantDirectories" )

		if( !autoGenerateVariantDirectories ) return

		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }

		VariantManager.getProductFlavors( project ).each { project.file( "${ moduleName }/src/${ it }" ).mkdirs( ) }
		VariantManager.getBuildTypes( project ).each { project.file( "${ moduleName }/src/${ it }" ).mkdirs( ) }
	}
}
