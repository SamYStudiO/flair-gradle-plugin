package flair.gradle.tasks.processes

import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSplashScreens extends VariantTask
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
		outputDir = project.file( "${ outputVariantDir }/package" )

		description = "Processes splash screens into ${ variant.name } ${ project.buildDir.name } directory"
	}

	public ProcessSplashScreens()
	{
		group = TaskGroup.DEFAULT.name
	}

	@TaskAction
	public void processSplashScreens( IncrementalTaskInputs inputs )
	{
		inputs.outOfDate {}
		inputs.removed { new File( outputDir , it.file.name ).delete( ) }

		for( File file : inputFiles )
		{
			if( file.exists( ) && project.fileTree( file.path ).size( ) > 0 )
			{
				project.copy {
					from file
					into outputDir

					include "*.png"
				}

				break
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directories.each { list.add( project.file( "${ moduleDir }/src/${ it }/splash_screens" ) ) }

		return list.reverse( )
	}
}