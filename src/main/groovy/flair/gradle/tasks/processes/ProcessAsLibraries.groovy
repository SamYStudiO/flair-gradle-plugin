package flair.gradle.tasks.processes

import flair.gradle.dependencies.Config
import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAsLibraries extends VariantTask
{
	@InputFiles
	Set<File> inputFiles

	@OutputDirectory
	File outputDir

	@Override
	void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/asLibraries" )

		description = "Processes as libraries into ${ variant.name } ${ project.buildDir.name } directory"
	}

	ProcessAsLibraries()
	{
		group = TaskGroup.DEFAULT.name
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void processAsLibraries()
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
		List<File> list = new ArrayList<File>( )

		variant.getDirectories( Variant.NamingType.CAPITALIZE_BUT_FIRST ).each {

			String s = it == "main" ? Config.AS_LIBRARY.name : it + Config.AS_LIBRARY.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
