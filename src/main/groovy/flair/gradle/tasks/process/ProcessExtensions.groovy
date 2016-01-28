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
class ProcessExtensions extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputFiles

	@OutputDirectories
	def Set<File> outputDirs

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputDirs = new ArrayList<File>( )

		outputDirs.add( project.file( "${ outputVariantDir }/extensions" ) )
		outputDirs.add( project.file( "${ outputVariantDir }/extracted_extensions" ) )
	}

	public ProcessExtensions()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processExtensions()
	{
		outputDirs.each { it.deleteDir( ) }

		findInputFiles( ).each { file ->

			if( file.exists( ) )
			{
				if( file.isDirectory( ) )
				{
					project.fileTree( file ).each {

						project.copy {
							from file
							into "${ outputVariantDir }/extensions"

							include "**/?*.ane"
						}
					}
				}
				else
				{
					project.copy {
						from file
						into "${ outputVariantDir }/extensions"
					}
				}
			}
		}

		File extensionsDir = project.file( "${ outputVariantDir }/extensions" )

		if( extensionsDir.exists( ) )
		{
			extensionsDir.listFiles( ).each { file ->
				project.copy {

					from project.zipTree( file )
					into "${ outputVariantDir }/extracted_extensions/${ file.name }"
				}
			}
		}
	}

	private Set<File> findInputFiles()
	{
		Set<File> extensionsFiles = project.configurations.getByName( Configurations.NATIVE_COMPILE.name ).files

		extensionsFiles.addAll( project.configurations.getByName( variant.platform.name + Configurations.NATIVE_COMPILE.name.capitalize( ) ).files )

		variant.productFlavors.each {
			extensionsFiles.addAll( project.configurations.getByName( it + Configurations.NATIVE_COMPILE.name.capitalize( ) ).files )
		}

		if( variant.buildType ) extensionsFiles.addAll( project.configurations.getByName( variant.buildType + Configurations.NATIVE_COMPILE.name.capitalize( ) ) )

		return extensionsFiles
	}
}
