package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.TexturePacker
import flair.gradle.extensions.FlairProperties
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PublishAtlases extends AbstractTask
{
	public PublishAtlases()
	{
		group = Groups.TEXTURE_PACKER.name
		description = ""
	}

	@TaskAction
	public void publishAtlases()
	{
		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME )
		ICli tp = new TexturePacker( )

		FileTree tree = project.fileTree( "${ moduleName }/src" )

		tree.each { file ->

			if( file.name.toLowerCase( ).indexOf( ".tps" ) >= 0 )
			{
				tp.addArgument( file.path )
			}
		}

		tp.execute( project )
	}
}
