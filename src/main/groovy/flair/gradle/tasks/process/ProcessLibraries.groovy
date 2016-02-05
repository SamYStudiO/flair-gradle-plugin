package flair.gradle.tasks.process

import flair.gradle.dependencies.Configurations
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessLibraries extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputFiles

	@OutputDirectory
	def File outputDir

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/libraries" )
	}

	public ProcessLibraries()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processLibraries()
	{
		outputDir.deleteDir( )

		inputFiles.each { file ->

			if( file.exists( ) )
			{
				if( file.isDirectory( ) )
				{
					project.fileTree( file ).each {
						project.copy {
							from file
							into "${ outputVariantDir }/libraries"
							include "**/?*.swc"
						}
					}
				}
				else
				{
					project.copy {
						from file
						into "${ outputVariantDir }/libraries"
					}
				}
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directoriesCapitalized.each {

			String s = it == "main" ? Configurations.LIBRARY.name : it + Configurations.LIBRARY.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
