package flair.gradle.tasks.process

import flair.gradle.cli.ICli
import flair.gradle.cli.Png2Atf
import flair.gradle.extensions.FlairProperty
import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.TaskGroup
import flair.gradle.utils.Variant
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
	def String excludeDrawables

	@Input
	def boolean generateAtf

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputDir = project.file( "${ outputVariantDir }/package/res" )

		List<String> list = extensionManager.getFlairProperty( variant , FlairProperty.PACKAGE_EXCLUDE_DRAWABLES ) as List<String>
		excludeDrawables = ""
		list.each { excludeDrawables += "drawable*-${ it }*/** " }
		excludeDrawables = excludeDrawables.trim( )

		generateAtf = extensionManager.getFlairProperty( variant , FlairProperty.GENERATE_ATF_TEXTURES_FROM_DRAWABLES )
	}

	public ProcessResources()
	{
		group = TaskGroup.DEFAULT.name
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

					if( excludeDrawables.length( ) ) exclude excludeDrawables.split( " " )
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

			project.file( "${ outputVariantDir }/package/res/values${ qualifiers }/" ).mkdirs( )
			File outputFile = project.file( "${ outputVariantDir }/package/res/values${ qualifiers }/values${ qualifiers }.xml" )

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
				outputFile.withOutputStream { writer -> XmlUtil.serialize( output , writer ) }
			}
		}
	}

	private generateAtfTextures()
	{
		ICli png2atf = new Png2Atf( )

		FileTree tree = project.fileTree( "${ outputVariantDir }/package/res" ) { include "**/*.png" }

		tree.each {

			String input = it.absolutePath
			String output = input.replaceAll( "\\.png" , "\\.atf" )

			png2atf.clearArguments( )
			png2atf.addArgument( "-i" )
			png2atf.addArgument( input )
			png2atf.addArgument( "-o" )
			png2atf.addArgument( output )

			png2atf.execute( project , variant.platform )
		}
	}

	private Set<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		variant.directories.each { list.add( project.file( "${ moduleDir }/src/${ it }/res" ) ) }

		return list
	}
}
