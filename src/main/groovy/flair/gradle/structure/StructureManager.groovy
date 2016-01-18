package flair.gradle.structure

import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.variants.VariantManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class StructureManager
{

	public static void updateVariantDirectories( Project project )
	{
		boolean autoGenerateVariantDirectories = PropertyManager.getProperty( project , "autoGenerateVariantDirectories" )

		if( !autoGenerateVariantDirectories ) return

		cleanVariantDirectory( project )

		String moduleName = PropertyManager.getProperty( project , "moduleName" )
		VariantManager.getProductFlavors( project ).each { project.file( "${ moduleName }/src/${ it }" ).mkdirs( ) }
		VariantManager.getBuildTypes( project ).each { project.file( "${ moduleName }/src/${ it }" ).mkdirs( ) }
	}

	private static void cleanVariantDirectory( Project project )
	{
		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }
	}
}
