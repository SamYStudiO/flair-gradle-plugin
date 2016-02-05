package flair.gradle.tasks.process

import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessIcons extends AbstractVariantTask
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
		outputDir = project.file( "${ outputVariantDir }/package/icons" )
	}

	public ProcessIcons()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processIcons()
	{
		outputDir.deleteDir( )

		for( File file : inputFiles )
		{
			if( file.exists( ) && project.fileTree( file.path ).size( ) > 0 )
			{
				project.copy {
					from file
					into outputDir
				}

				break
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directories.each { list.add( project.file( "${ moduleDir }/src/${ it }/icons" ) ) }

		return list.reverse( )
	}
}
