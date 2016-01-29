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

		findInputFiles( ).each { file ->

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
		Set<File> libraryFiles = project.configurations.getByName( Configurations.LIBRARY.name ).files

		libraryFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.LIBRARY.name.capitalize( ) ).files )

		variant.productFlavors.each {
			libraryFiles.addAll( project.configurations.getByName( it + Configurations.LIBRARY.name.capitalize( ) ).files )
		}

		if( variant.buildType ) libraryFiles.addAll( project.configurations.getByName( variant.buildType + Configurations.LIBRARY.name.capitalize( ) ) )

		return libraryFiles
	}
}
