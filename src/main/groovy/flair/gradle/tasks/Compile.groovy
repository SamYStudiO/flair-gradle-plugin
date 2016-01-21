package flair.gradle.tasks

import flair.gradle.cli.ICli
import flair.gradle.cli.Mxmlc
import flair.gradle.extensions.Properties
import flair.gradle.variants.Variant
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Compile extends AbstractVariantTask
{
	ICli cli = new Mxmlc( )

	String input

	String output

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

		cli.reset( )

		addSourcePaths( )
		cli.addArgument( "-debug=true" )
		addLibraryPaths( )
		addOutput( )
		addMainClass( )


		cli.arguments.each { arg -> println( arg ) }
		cli.execute( project )
	}

	public void addSourcePaths()
	{
		cli.addArgument( "-source-path+=" + input + "/src/main/actionscript" )
		cli.addArgument( "-source-path+=" + input + "/src/main/fonts" )
		cli.addArgument( "-source-path+=" + input + "/src/main/generated" )
		cli.addArgument( "-source-path+=" + input + "/libs_as" )
	}

	public void addLibraryPaths()
	{
		cli.addArgument( "-library-path+=" + input + "/libs_swc" )
	}

	public void addOutput()
	{
		cli.addArgument( "-output" )
		cli.addArgument( output + "/" + variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) + ".swf" )
	}

	public void addMainClass()
	{
		cli.addArgument( input + "/src/main/actionscript/" + extensionManager.getFlairProperty( variant , Properties.MAIN_CLASS.name ).toString( ).split( "\\." ).join( "/" ) + ".as" )
	}
}
