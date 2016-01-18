package flair.gradle.structure

import flair.gradle.extensions.PropertyManager
import flair.gradle.variants.VariantManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		boolean autoGenerateVariantDirectories = PropertyManager.getProperty( project , "autoGenerateVariantDirectories" )

		if( !autoGenerateVariantDirectories ) return

		String moduleName = PropertyManager.getProperty( project , "moduleName" )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }

		VariantManager.getProductFlavors( project ).each { project.file( "${ moduleName }/src/${ it }" ).mkdirs( ) }
		VariantManager.getBuildTypes( project ).each { project.file( "${ moduleName }/src/${ it }" ).mkdirs( ) }
	}
}

