package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.Mxmlc
import flair.gradle.dependencies.Configurations
import flair.gradle.extensions.Properties
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Compile extends AbstractVariantTask
{
	private ICli cli = new Mxmlc( )

	private String input

	private String output

	public Compile()
	{
		group = Groups.BUILD.name
		description = ""
	}

	@TaskAction
	public void compile()
	{
		input = "${ project.projectDir }/${ extensionManager.getFlairProperty( Properties.MODULE_NAME.name ) }"
		output = "${ project.buildDir }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }"


		cli.clearArguments( )

		if( variant.platform == Platforms.DESKTOP ) cli.addArgument( "+configname=air" ) else cli.addArgument( "+configname=airmobile" )

		if( extensionManager.getFlairProperty( variant , Properties.DEBUG.name ) ) cli.addArgument( "-debug=true" )

		addSourcePaths( )
		addAsLibraryPaths( )
		addLibraryPaths( )
		addConstants( )
		addCustomArguments( )
		addOutput( )
		addMainClass( )

		cli.execute( project )
	}

	private void addSourcePaths()
	{
		List<String> list = new ArrayList<String>( )

		list.add( variant.buildType )
		list.addAll( variant.productFlavors )
		list.add( "main" )

		File file

		list.each {

			file = project.file( "${ input }/src/${ it }/actionscript" )
			if( file.exists( ) ) cli.addArgument( "-source-path+=${ file.path }" )

			file = project.file( "${ input }/src/${ it }/fonts" )
			if( file.exists( ) ) cli.addArgument( "-source-path+=${ file.path }" )
		}

		file = project.file( "${ input }/src/main/generated" )
		if( file.exists( ) ) cli.addArgument( "-source-path+=${ file.path }" )
	}

	private void addAsLibraryPaths()
	{
		project.configurations.getByName( Configurations.COMPILE.name ).files.each {

			cli.addArgument( "-source-path+=${ it }" )
		}

		project.configurations.getByName( "${ variant.platform.name }Compile" ).files.each {

			cli.addArgument( "-source-path+=${ it }" )
		}

		variant.productFlavors.each {

			project.configurations.getByName( "${ it }Compile" ).files.each {

				cli.addArgument( "-source-path+=${ it }" )
			}
		}

		project.configurations.getByName( "${ variant.buildType }Compile" ).files.each {

			cli.addArgument( "-source-path+=${ it }" )
		}
	}

	private void addLibraryPaths()
	{
		project.configurations.getByName( Configurations.LIBRARY_COMPILE.name ).files.each {

			cli.addArgument( "-library-path+=${ it }" )
		}

		project.configurations.getByName( "${ variant.platform.name }LibraryCompile" ).files.each {

			cli.addArgument( "-library-path+=${ it }" )
		}

		variant.productFlavors.each {

			project.configurations.getByName( "${ it }LibraryCompile" ).files.each {

				cli.addArgument( "-library-path+=${ it }" )
			}
		}

		project.configurations.getByName( "${ variant.buildType }LibraryCompile" ).files.each {

			cli.addArgument( "-library-path+=${ it }" )
		}
	}

	private addConstants()
	{
		Platforms.values( ).each {

			cli.addArgument( "-define+=PLATFORM::${ it.name.toUpperCase( ) },${ it == variant.platform }" )
		}

		extensionManager.allActivePlatformProductFlavors.each {

			cli.addArgument( "-define+=PRODUCT_FLAVOR::${ it.name.toUpperCase( ) },${ variant.productFlavors.indexOf( it.name ) >= 0 }" )
		}

		extensionManager.allActivePlatformBuildTypes.each {

			cli.addArgument( "-define+=BUILD_TYPE::${ it.name.toUpperCase( ) },${ it.name == variant.buildType }" )
		}
	}

	private void addCustomArguments()
	{
		cli.addArguments( extensionManager.getFlairProperty( variant , Properties.COMPILE_OPTIONS.name ) as List<String> )
	}

	private void addOutput()
	{
		cli.addArgument( "-output" )
		cli.addArgument( "${ output }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }.swf" )
	}

	private void addMainClass()
	{
		String pClass = extensionManager.getFlairProperty( variant , Properties.COMPILE_MAIN_CLASS.name )

		List<String> list = new ArrayList<String>( )

		list.add( variant.buildType )
		list.addAll( variant.productFlavors )
		list.add( "main" )

		File file

		for( String s in list )
		{
			file = project.file( "${ input }/src/${ s }/actionscript/${ pClass.split( "\\." ).join( "/" ) }.as" )

			if( file.exists( ) )
			{
				cli.addArgument( file.path )
				return
			}
		}

		throw new Exception( "Mxml cannot find Main Class from ${ file.path }" )
	}
}
