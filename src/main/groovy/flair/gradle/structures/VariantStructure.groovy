package flair.gradle.structures

import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.FlairProperties
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantStructure implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager

		boolean autoGenerateVariantDirectories = extensionManager.getFlairProperty( FlairProperties.AUTO_GENERATE_VARIANT_DIRECTORIES.name )

		if( !autoGenerateVariantDirectories ) return

		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }

		extensionManager.allActivePlatformProductFlavors.each { project.file( "${ moduleName }/src/${ it.name }" ).mkdirs( ) }
		extensionManager.allActivePlatformBuildTypes.each { project.file( "${ moduleName }/src/${ it.name }" ).mkdirs( ) }
	}
}

