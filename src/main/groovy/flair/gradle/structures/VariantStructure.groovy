package flair.gradle.structures

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class VariantStructure implements IStructure
{
	@Override
	void create( Project project , File source )
	{
		IExtensionManager extensionManager = project.flair as IExtensionManager

		boolean autoGenerateVariantDirectories = extensionManager.getFlairProperty( FlairProperty.AUTO_GENERATE_VARIANT_DIRECTORIES )

		if( !autoGenerateVariantDirectories ) return

		String moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }

		extensionManager.allActivePlatformVariants.each {
			it.directories.each { directory -> project.file( "${ moduleName }/src/${ directory }" ).mkdirs( ) }
		}
	}
}

