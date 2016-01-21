package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Clean extends AbstractTask
{
	public Clean()
	{
		group = Groups.BUILD.name
		description = ""
	}

	@TaskAction
	public void clean()
	{
		project.buildDir.deleteDir( )
	}
}
