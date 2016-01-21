package flair.gradle.structure

import flair.gradle.extensions.IExtensionManager
import flair.gradle.extensions.Properties
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

		boolean autoGenerateVariantDirectories = extensionManager.getFlairProperty( Properties.AUTO_GENERATE_VARIANT_DIRECTORIES.name )

		if( !autoGenerateVariantDirectories ) return

		String moduleName = extensionManager.getFlairProperty( Properties.MODULE_NAME.name )

		project.file( "${ moduleName }/src/" ).listFiles( ).each { file -> if( file.isDirectory( ) && file.listFiles( ).size( ) == 0 ) file.deleteDir( ) }

		extensionManager.allActivePlatformProductFlavors.each { project.file( "${ moduleName }/src/${ it.name }" ).mkdirs( ) }
		extensionManager.allActivePlatformBuildTypes.each { project.file( "${ moduleName }/src/${ it.name }" ).mkdirs( ) }
	}
}

