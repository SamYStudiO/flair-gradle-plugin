package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.Mxmlc
import flair.gradle.variants.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PreparePackage extends AbstractVariantTask
{
	protected ICli cli = new Mxmlc( )

	@InputFiles
	def Set<File> inputFiles

	@OutputFile
	def File outputFile

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputFile = project.file( "${ outputVariantDir }/package/app_descriptor.xml" )
	}

	public PreparePackage()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void prepare()
	{
		project.copy {

			from "${ outputVariantDir.path }/app_descriptor.xml"
			into "${ outputVariantDir.path }/package"
		}
	}

	private List<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		list.add( project.file( "${ outputVariantDir.path }/app_descriptor.xml" ) )

		return list
	}
}
