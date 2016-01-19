package flair.gradle.structure

import flair.gradle.extensions.IPlatformExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		IPlatformExtensionManager extensionManager = project.flair as IPlatformExtensionManager

		boolean autoGenerateVariantDirectories = extensionManager.getFlairProperty( "autoGenerateVariantDirectories" )

		if( !autoGenerateVariantDirectories ) return

		String moduleName = extensionManager.getFlairProperty( "moduleName" )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }

		extensionManager.allProductFlavors.each { project.file( "${ moduleName }/src/${ it.name }" ).mkdirs( ) }
		extensionManager.allBuildTypes.each { project.file( "${ moduleName }/src/${ it.name }" ).mkdirs( ) }
	}
}

