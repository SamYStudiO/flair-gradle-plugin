package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.Mxmlc
import flair.gradle.extensions.FlairProperty
import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platform
import flair.gradle.variants.Variant
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Compile extends AbstractVariantTask
{
	protected ICli cli = new Mxmlc( )

	@InputFiles
	def Set<File> inputFiles

	@OutputFile
	def File outputFile

	@Input
	def boolean debug

	@Input
	def String mainClass

	@Input
	def String compilerOptions

	@Override
	public void setVariant( Variant variant )
	{
		super.variant = variant

		inputFiles = findInputFiles( )
		outputFile = project.file( "${ outputVariantDir }/package/${ variant.getNameWithType( Variant.NamingType.UNDERSCORE ) }.swf" )

		debug = extensionManager.getFlairProperty( variant , FlairProperty.DEBUG )
		mainClass = extensionManager.getFlairProperty( variant , FlairProperty.COMPILER_MAIN_CLASS )
		compilerOptions = ( extensionManager.getFlairProperty( variant , FlairProperty.COMPILER_OPTIONS ) as List<String> ).join( " " )
	}

	public Compile()
	{
		group = TaskGroup.BUILD.name
		description = ""
	}

	@TaskAction
	public void compile()
	{
		cli.clearArguments( )

		if( variant.platform == Platform.DESKTOP ) cli.addArgument( "+configname=air" ) else cli.addArgument( "+configname=airmobile" )

		if( debug ) cli.addArgument( "-debug=true" )

		//as files
		cli.addArgument( "-source-path+=${ project.file( "${ outputVariantDir.path }/classes" ) }" )

		//as library files
		cli.addArgument( "-source-path+=${ project.file( "${ outputVariantDir.path }/asLibraries" ) }" )

		//swc library files
		cli.addArgument( "-library-path+=${ project.file( "${ outputVariantDir.path }/libraries" ) }" )

		//ane library files
		cli.addArgument( "-external-library-path+=${ project.file( "${ outputVariantDir.path }/extensions" ) }" )

		addConstants( )

		// custom options
		if( compilerOptions.length(  ) ) cli.addArguments( compilerOptions.split( " " ) )

		// swf output
		cli.addArgument( "-output" )
		cli.addArgument( project.file( "${ outputVariantDir }/package/${ variant.getNameWithType( Variant.NamingType.UNDERSCORE ) }.swf" ).path )

		// main class
		cli.addArgument( project.file( "${ outputVariantDir }/classes/${ mainClass.split( "\\." ).join( "/" ) }.as" ).path )

		cli.execute( project , variant.platform )
	}

	private addConstants()
	{
		PluginManager.getCurrentPlatforms( project ).each {

			cli.addArgument( "-define+=PLATFORM::${ it.name.toUpperCase( ) },${ it == variant.platform }" )
		}

		extensionManager.allActivePlatformProductFlavors.each {

			cli.addArgument( "-define+=PRODUCT_FLAVOR::${ it.toUpperCase( ) },${ variant.productFlavors.indexOf( it ) >= 0 }" )
		}

		extensionManager.allActivePlatformBuildTypes.each {

			cli.addArgument( "-define+=BUILD_TYPE::${ it.toUpperCase( ) },${ it == variant.buildType }" )
		}
	}

	private List<File> findInputFiles()
	{
		List<File> list = new ArrayList<File>( )

		list.add( project.file( "${ outputVariantDir.path }/classes" ) )
		list.add( project.file( "${ outputVariantDir.path }/libraries" ) )
		list.add( project.file( "${ outputVariantDir.path }/asLibraries" ) )
		list.add( project.file( "${ outputVariantDir.path }/extensions" ) )

		return list
	}
}
