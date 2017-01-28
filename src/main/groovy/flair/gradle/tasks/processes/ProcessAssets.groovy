package flair.gradle.tasks.processes

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
	Set<File> inputFiles

	@OutputFiles
	Set<File> outputFiles

	@OutputDirectories
	Set<File> outputDirs

	@Override
	void setVariant( Variant variant )
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

	ProcessAssets()
	{
		group = TaskGroup.DEFAULT.name
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void processAssets()
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
