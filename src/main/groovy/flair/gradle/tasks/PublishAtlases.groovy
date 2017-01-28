package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.TexturePacker
import flair.gradle.extensions.FlairProperty
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PublishAtlases extends AbstractTask
{
	PublishAtlases()
	{
		group = TaskGroup.TEXTURE_PACKER.name
		description = "Publishes all TexturePacker atlases within project"
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void publishAtlases()
	{
		String moduleName = extensionManager.getFlairProperty( FlairProperty.MODULE_NAME )
		ICli tp = new TexturePacker( )

		FileTree tree = project.fileTree( "${ moduleName }/src" )

		tree.each { file ->

			if( file.name.toLowerCase( ).indexOf( ".tps" ) >= 0 )
			{
				tp.addArgument( file.path )
			}
		}

		try
		{
			tp.execute( project )
		}
		catch( Exception e )
		{
			throw new Exception( "Error publishing atlases, you added TexturePacker plugin but have you install its command line tool? check https://github.com/SamYStudiO/flair-gradle-plugin/wiki/TexturePacker-plugin for more information.")
		}
	}
}
