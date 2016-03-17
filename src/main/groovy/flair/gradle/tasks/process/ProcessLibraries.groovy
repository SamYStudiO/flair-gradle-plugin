package flair.gradle.tasks.process

import flair.gradle.dependencies.Config
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.TaskGroup
import flair.gradle.utils.Variant
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

		description = "Processes swc libraries into ${ variant.name } ${ project.buildDir.name } directory"
	}

	public ProcessLibraries()
	{
		group = TaskGroup.DEFAULT.name
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

		variant.getDirectories( Variant.NamingType.CAPITALIZE_BUT_FIRST ).each {

			String s = it == "main" ? Config.LIBRARY.name : it + Config.LIBRARY.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
