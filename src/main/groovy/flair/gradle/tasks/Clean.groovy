package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Clean extends AbstractTask
{
	public Clean()
	{
		group = TaskGroup.BUILD.name
		description = "Deletes ${ project.buildDir.name } directory"
	}

	@TaskAction
	public void clean()
	{
		project.buildDir.deleteDir( )
	}
}
