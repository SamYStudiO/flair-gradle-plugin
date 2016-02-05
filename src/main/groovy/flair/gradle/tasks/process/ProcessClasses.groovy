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
class ProcessClasses extends AbstractVariantTask
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
		outputDir = project.file( "${ outputVariantDir }/classes" )
	}

	public ProcessClasses()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processClasses()
	{
		outputDir.deleteDir( )

		inputFiles.each { file ->

			if( file.exists( ) )
			{
				project.copy {
					from file
					into "${ outputVariantDir }/classes"
				}
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directoriesCapitalized.each {

			String s = it == "main" ? Configurations.SOURCE.name : it + Configurations.SOURCE.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
