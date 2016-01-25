package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.TexturePacker
import flair.gradle.extensions.FlairProperties
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PublishAtlases extends AbstractVariantTask
{
	public PublishAtlases()
	{
		group = Groups.TEXTURE_PACKER.name
		description = ""
	}

	@TaskAction
	public void publishAtlases()
	{
		String moduleName = extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name )
		ICli tp = new TexturePacker( )

		List<String> list = new ArrayList<String>( )

		list.add( "main" )
		list.add( variant.platform.name )
		variant.productFlavors.each { list.add( it ) }
		list.add( variant.buildType )

		list.each {

			FileTree tree = project.fileTree( "${ moduleName }/src/${ it }/" )

			tree.each { file ->

				if( file.name.toLowerCase( ).indexOf( ".tps" ) >= 0 )
				{
					tp.addArgument( file.path )
				}
			}
		}

		tp.execute( project )
	}
}
