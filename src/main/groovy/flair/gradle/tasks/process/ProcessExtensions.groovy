package flair.gradle.tasks.process

import flair.gradle.dependencies.Config
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.TaskGroup
import flair.gradle.utils.Variant
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

		description = "Processes natives extensions and unzipped extensions into ${ variant.name } ${ project.buildDir.name } directory"
	}

	public ProcessExtensions()
	{
		group = TaskGroup.DEFAULT.name
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

		variant.getDirectories( Variant.NamingType.CAPITALIZE_BUT_FIRST ).each {

			String s = it == "main" ? Config.NATIVE_LIBRARY.name : it + Config.NATIVE_LIBRARY.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
