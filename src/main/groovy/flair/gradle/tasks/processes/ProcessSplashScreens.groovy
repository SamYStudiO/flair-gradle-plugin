package flair.gradle.tasks.processes

import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSplashScreens extends VariantTask
{
	@InputFiles
	Set<File> inputFiles

	@OutputFiles
	Set<File> outputFiles

	File outputDir

	@Override
	void setVariant( Variant variant )
	{
		super.variant = variant

		outputDir = project.file( "${ outputVariantDir }/package" )
		inputFiles = findInputFiles( )
		outputFiles = new ArrayList<File>( )
		for( File file : inputFiles )
		{
			if( file.exists( ) && project.fileTree( file.path ).size( ) > 0 )
			{
				project.fileTree( file ).each { outputFiles.add( project.file( "${ outputVariantDir }/package/${ it.name }" ) ) }

				break
			}
		}

		description = "Processes splash screens into ${ variant.name } ${ project.buildDir.name } directory"
	}

	ProcessSplashScreens()
	{
		group = TaskGroup.DEFAULT.name
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void processSplashScreens( IncrementalTaskInputs inputs )
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
