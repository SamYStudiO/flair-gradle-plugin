package flair.gradle.tasks.process

import flair.gradle.dependencies.Configurations
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessLibraries extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputDirs

	@OutputDirectories
	def Set<File> outputDirs

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputDirs = getInputFiles( )
		outputDirs = new HashSet<File>( )

		inputDirs.each {

			File file = project.file( "${ outputVariantDir }/libraries" )
			outputDirs.add( file )
		}
	}

	public ProcessLibraries()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processLibraries()
	{
		//outputDirs.each { it.deleteDir( ) }

		getInputFiles( ).each { file ->

			if( file.exists( ) )
			{
				project.copy {
					from file
					into file.isDirectory(  ) ? "${ outputVariantDir }/classes" : "${ outputVariantDir }/libraries"
				}
			}
		}
	}

	private Set<File> getInputFiles()
	{
		Set<File> packagedFiles = project.configurations.getByName( Configurations.LIBRARY_COMPILE.name ).files

		packagedFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.LIBRARY_COMPILE.name.capitalize( ) ).files )

		variant.productFlavors.each {
			packagedFiles.addAll( project.configurations.getByName( it + Configurations.LIBRARY_COMPILE.name.capitalize( ) ).files )
		}

		if( variant.buildType ) packagedFiles.addAll( project.configurations.getByName( variant.buildType + Configurations.LIBRARY_COMPILE.name.capitalize( ) ) )

		return packagedFiles
	}
}
