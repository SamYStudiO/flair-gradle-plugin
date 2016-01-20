package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Compile extends AbstractVariantTask
{
	public Compile()
	{
		group = Groups.BUILD.name
		description = ""
	}

	@TaskAction
	public void compile()
	{
		//println( "test> " + platform + "--" + productFlavor + "--" + buildType )

		ByteArrayOutputStream output = new ByteArrayOutputStream( )

		/*project.exec {
			executable AIRSDKManager.getMxmlcPath( project )
			//args

			standardOutput = output
		}*/

		println( output.toString( ) )
	}
}
