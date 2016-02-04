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
class ProcessAsLibraries extends AbstractVariantTask
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
		outputDir = project.file( "${ outputVariantDir }/asLibraries" )
	}

	public ProcessAsLibraries()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processAsLibraries()
	{
		outputDir.deleteDir( )

		inputFiles.each { file ->

			if( file.exists( ) )
			{
				project.copy {
					from file
					into "${ outputVariantDir }/asLibraries"
				}
			}
		}
	}

	private Set<File> findInputFiles()
	{
		Set<File> asLibraryFiles = project.configurations.getByName( Configurations.AS_LIBRARY.name ).files

		asLibraryFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.AS_LIBRARY.name.capitalize( ) ).files )

		variant.productFlavors.each {
			asLibraryFiles.addAll( project.configurations.getByName( it + Configurations.AS_LIBRARY.name.capitalize( ) ).files )
		}

		if( variant.buildType ) asLibraryFiles.addAll( project.configurations.getByName( variant.buildType + Configurations.AS_LIBRARY.name.capitalize( ) ) )

		return asLibraryFiles
	}
}
