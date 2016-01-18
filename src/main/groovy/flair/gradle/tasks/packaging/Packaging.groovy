package flair.gradle.tasks.packaging

import flair.gradle.tasks.AbstractVariantTask
import flair.gradle.tasks.Group
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Packaging extends AbstractVariantTask
{
	public Packaging()
	{
		group = Group.PACKAGE.name
		description = ""
	}

	@TaskAction
	public void packaging()
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
