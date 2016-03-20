package flair.gradle.tasks.process

import flair.gradle.dependencies.Config
import flair.gradle.tasks.TaskGroup
import flair.gradle.tasks.VariantTask
import flair.gradle.utils.Variant
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectories
import org.gradle.api.tasks.OutputFiles
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessAssets extends VariantTask
{
	@InputFiles
	def Set<File> inputFiles

	@OutputFiles
	def Set<File> outputFiles

	@OutputDirectories
	def Set<File> outputDirs

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputFiles = new ArrayList<File>( )
		outputDirs = new ArrayList<File>( )

		inputFiles.each {

			File file = project.file( "${ outputVariantDir }/package/${ it.name }" )

			if( file.isDirectory( ) ) outputDirs.add( file ) else outputFiles.add( file )
		}

		description = "Processes assets directories into ${ variant.name } ${ project.buildDir.name } directory"
	}

	public ProcessAssets()
	{
		group = TaskGroup.DEFAULT.name
	}

	@TaskAction
	public void processAssets()
	{
		outputFiles.each { it.delete( ) }
		outputDirs.each { it.deleteDir( ) }

		inputFiles.each { file ->

			if( file.exists( ) )
			{
				if( file.isDirectory( ) )
				{
					project.copy {
						from file
						into "${ outputVariantDir }/package/${ file.name }"

						includeEmptyDirs = false
					}
				}
				else
				{
					project.copy {
						from file
						into "${ outputVariantDir }/package"
					}
				}
			}
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.getDirectories( Variant.NamingType.CAPITALIZE_BUT_FIRST ).each {

			String s = it == "main" ? Config.PACKAGE.name : it + Config.PACKAGE.name.capitalize( )

			list.addAll( project.configurations.getByName( s ).files )
		}

		return list
	}
}
