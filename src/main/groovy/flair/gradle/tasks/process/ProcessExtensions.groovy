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

		inputFiles.each { file ->

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
		List<File> list = new ArrayList<File>( )

		variant.directoriesCapitalized.each {

			String s = it == "main" ? Configurations.NATIVE_LIBRARY.name : it + Configurations.NATIVE_LIBRARY.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
