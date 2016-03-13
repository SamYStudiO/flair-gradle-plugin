package flair.gradle.structures.fdt

import flair.gradle.extensions.FlairProperty
import flair.gradle.extensions.IExtensionManager
import flair.gradle.structures.IStructure
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class FdtCleanup implements IStructure
{
	@Override
	public void create( Project project , File source )
	{
		String moduleName = ( project.flair as IExtensionManager ).getFlairProperty( FlairProperty.MODULE_NAME )

		if( project.file( ".classpath" ).exists( ) )
		{
			project.file( "src" ).deleteDir( )
			project.file( "bin" ).deleteDir( )
			project.file( ".classpath" ).delete( )
			project.file( ".settings/org.eclipse.buildship.core.prefs" ).delete( )
		}
	}
}
