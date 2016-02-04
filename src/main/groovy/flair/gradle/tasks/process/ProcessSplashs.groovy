package flair.gradle.tasks.process

import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessSplashs extends AbstractVariantTask
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
	}

	public ProcessSplashs()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processSplashs( IncrementalTaskInputs inputs )
	{
		inputs.outOfDate {}
		inputs.removed { new File( outputDir , it.file.name ).delete( ) }

		for( File file : inputFiles )
		{
			if( file.exists( ) && project.fileTree( file.path ).size(  ) > 0 )
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

		list.add( project.file( "${ moduleDir }/src/${ variant.platform.name }/splashs" ) )
		variant.productFlavors.each { list.add( project.file( "${ moduleDir }/src/${ it }/splashs" ) ) }
		if( variant.buildType ) list.add( project.file( "${ moduleDir }/src/${ variant.buildType }/splashs" ) )
		variant.productFlavors.each { list.add( project.file( "${ moduleDir }/src/${ variant.platform.name }_${ it }/splashs" ) ) }
		list.add( project.file( "${ moduleDir }/src/${ variant.platform.name }_${ variant.buildType }/splashs" ) )
		list.add( project.file( "${ moduleDir }/src/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }/splashs" ) )

		return list
	}
}
