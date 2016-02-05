package flair.gradle.tasks.process

import flair.gradle.cli.ICli
import flair.gradle.cli.Png2Atf
import flair.gradle.extensions.FlairProperties
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Groups
import flair.gradle.variants.Variant
import groovy.xml.XmlUtil
import org.gradle.api.file.FileTree
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class ProcessResources extends AbstractVariantTask
{
	@InputFiles
	def Set<File> inputFiles

	@OutputDirectory
	def File outputDir

	@Input
	def List<String> excludeResources

	@Input
	def boolean generateAtf

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/package/resources" )

		excludeResources = extensionManager.getFlairProperty( variant , FlairProperties.PACKAGE_EXCLUDE_RESOURCES ) as List<String>
		generateAtf = extensionManager.getFlairProperty( variant , FlairProperties.GENERATE_ATF_TEXTURES_FROM_DRAWABLES )
	}

	public ProcessResources()
	{
		group = Groups.DEFAULT.name
		description = ""
	}

	@TaskAction
	public void processResources()
	{
		outputDir.deleteDir( )

		inputFiles.each { file ->

			if( file.exists( ) )
			{
				project.copy {
					from file
					into outputDir

					exclude excludeResources
					exclude "**/value*/**"

					includeEmptyDirs = false
				}

				processResourceValues( file )
			}
		}

		if( generateAtf ) generateAtfTextures( )
	}

	protected String processResourceValues( File resourceDir )
	{
		project.fileTree( resourceDir ) { include "values*/?*.xml" }.each { file ->

			// TODO maybe need to check if parent folder is not a screen here since we may add resources for screens
			String qualifiers = file.parentFile.name.replace( "values" , "" )

			project.file( "${ outputVariantDir }/package/resources/values${ qualifiers }/" ).mkdirs( )
			File outputFile = project.file( "${ outputVariantDir }/package/resources/values${ qualifiers }/values${ qualifiers }.xml" )

			if( !outputFile.exists( ) ) outputFile.createNewFile( )

			Node output

			if( outputFile.text.isEmpty( ) ) output = new Node( null , "resources" ) else output = new XmlParser( ).parse( outputFile )

			Node xml = new XmlParser( ).parse( file )

			xml.children( ).each { Node node ->

				Node old = output.children( ).find { it.name( ) == node.name( ) && it.'@name' == node.'@name' } as Node

				if( old ) output.remove( old )

				output.append( node )
			}

			if( output.children( ).size( ) > 0 )
			{
				outputFile.withWriter { writer -> XmlUtil.serialize( output , writer ) }
			}
		}
	}

	private generateAtfTextures()
	{
		ICli png2atf = new Png2Atf( )

		FileTree tree = project.fileTree( "${ outputVariantDir }/package/resources/" ) { include "**/*.png" }

		tree.each {

			String input = it.absolutePath
			String output = input.replaceAll( "\\.png" , "\\.atf" )

			png2atf.clearArguments( )
			png2atf.addArgument( "-i" )
			png2atf.addArgument( input )
			png2atf.addArgument( "-o" )
			png2atf.addArgument( output )

			png2atf.execute( project )
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directories.each { list.add( project.file( "${ moduleDir }/src/${ it }/resources" ) ) }

		return list
	}
}
