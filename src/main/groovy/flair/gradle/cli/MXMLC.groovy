package flair.gradle.cli

import flair.gradle.variants.Platform
import flair.gradle.utils.AIRSDKManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class MXMLC implements ICli
{
	protected Project project

	protected Platform platform

	protected String productFlavor

	protected String buildType

	protected List<String> arguments

	public MXMLC( Project project , Platform platform , String productFlavor , String buildType )
	{
		this.project = project
		this.platform = platform
		this.productFlavor = productFlavor ?: ""
		this.buildType = buildType ?: ""
		this.arguments = new ArrayList<String>( )

		println( ">>>>>>>>>>>>>>>>>>>>>" + project.flair.android.buildTypes )

		arguments.add( "-source-path+=" + project.projectDir.absolutePath + "/" + project.flair.moduleName.toString( ) + "/src/main/actionscript" )
		arguments.add( "-source-path+=" + project.projectDir.absolutePath + "/" + project.flair.moduleName.toString( ) + "/src/main/fonts" )
		arguments.add( "-source-path+=" + project.projectDir.absolutePath + "/" + project.flair.moduleName.toString( ) + "/src/main/generated" )
		arguments.add( "-source-path+=" + project.projectDir.absolutePath + "/" + project.flair.moduleName.toString( ) + "/libs_as" )
		arguments.add( "-library-path+=" + project.projectDir.absolutePath + "/" + project.flair.moduleName.toString( ) + "/libs_swc" )
		arguments.add( "-output" )
		arguments.add( project.buildDir.absolutePath + "/" + project.name + "_" + productFlavor + "_" + buildType + ".swf" )
		arguments.add( project.projectDir.absolutePath + "/" + project.flair.moduleName.toString( ) + "/src/main/actionscript/qsd/MainAndroid.as" )

		arguments.each { arg -> println( arg )
		}


	}

	@Override
	public String getExecutable()
	{
		return AIRSDKManager.getMXMLCPath( project )
	}

	@Override
	public List<String> getArguments()
	{
		return arguments
	}

	//protected getSource
}
