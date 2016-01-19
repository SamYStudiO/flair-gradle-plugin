package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Install extends AbstractVariantTask
{
	public Install()
	{
		group = Group.INSTALL.name
		description = ""
	}

	@TaskAction
	public void install()
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
