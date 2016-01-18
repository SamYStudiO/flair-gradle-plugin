package flair.gradle.tasks

import flair.gradle.tasks.Group
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Clean extends DefaultTask
{
	public Clean()
	{
		group = Group.BUILD.name
		description = ""
	}

	@TaskAction
	public void clean()
	{
		project.buildDir.delete( )
	}
}
