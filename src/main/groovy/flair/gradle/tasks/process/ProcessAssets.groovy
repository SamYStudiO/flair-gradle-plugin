package flair.gradle.tasks.process

import flair.gradle.dependencies.Configurations
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAssets extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputFiles

	@OutputFiles
	def Set<File> outputFiles

	@OutputDirectories
	def Set<File> outputDirs

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputFiles = new ArrayList<File>( )
		outputDirs = new ArrayList<File>( )

		inputFiles.each {

			File file = project.file( "${ outputVariantDir }/package/${ it.name }" )

			if( file.isDirectory( ) ) outputDirs.add( file ) else outputFiles.add( file )
		}
	}

	public ProcessAssets()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processAssets()
	{
		outputFiles.each { it.delete( ) }
		outputDirs.each { it.deleteDir( ) }

		inputFiles.each { file ->

			if( file.exists( ) )
			{
				if( file.isDirectory( ) )
				{
					project.copy {
						from file
						into "${ outputVariantDir }/package/${ file.name }"

						includeEmptyDirs = false
					}
				}
				else
				{
					project.copy {
						from file
						into "${ outputVariantDir }/package"
					}
				}
			}
		}
	}

	private Set<File> findInputFiles()
	{
		Set<File> packageFiles = project.configurations.getByName( Configurations.PACKAGE.name ).files

		packageFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.PACKAGE.name.capitalize( ) ).files )

		variant.productFlavors.each {
			packageFiles.addAll( project.configurations.getByName( it + Configurations.PACKAGE.name.capitalize( ) ).files )
		}

		if( variant.buildType ) packageFiles.addAll( project.configurations.getByName( variant.buildType + Configurations.PACKAGE.name.capitalize( ) ) )

		return packageFiles
	}
}
