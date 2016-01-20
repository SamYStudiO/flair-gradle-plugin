package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Packaging extends AbstractVariantTask
{
	public Packaging()
	{
		group = Groups.PACKAGE.name
		description = ""
	}

	@TaskAction
	public void packaging()
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream( )

		/*project.exec {
			executable AIRSDKManager.getMxmlcPath( project )
			//args

			standardOutput = output
		}*/

		println( output.toString( ) )
	}
}
