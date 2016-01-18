package flair.gradle.tasks

import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Group
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Launch extends AbstractVariantTask
{
	public Launch()
	{
		group = Group.LAUNCH.name
		description = ""
	}

	@TaskAction
	public void launch()
	{
		ByteArrayOutputStream output = new ByteArrayOutputStream( )

		/*project.exec {
			executable AIRSDKManager.getMXMLCPath( project )
			//args

			standardOutput = output
		}*/

		println( output.toString( ) )
	}
}
