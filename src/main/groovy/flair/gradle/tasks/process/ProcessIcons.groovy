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
	def Set<File> inputDirs

	@OutputDirectory
	def File outputDir

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputDirs = getInputFiles( )
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
		//outputDir.deleteDir( )

		getInputFiles( ).each { file ->

			if( file.exists( ) )
			{
				project.copy {
					from file
					into outputDir
				}
			}
		}
	}

	private Set<File> getInputFiles()
	{
		Set<File> set = new HashSet<File>( )

		set.add( project.file( "${ moduleDir }/src/${ variant.platform.name }/icons/" ) )
		variant.productFlavors.each { set.add( project.file( "${ moduleDir }/src/${ it }/icons/" ) ) }
		if( variant.buildType ) set.add( project.file( "${ moduleDir }/src/${ variant.buildType }/icons/" ) )

		return set
	}
}
