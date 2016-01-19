package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Clean extends AbstractFlairTask
{
	public Clean()
	{
		group = Group.BUILD.name
		description = ""
	}

	@TaskAction
	public void clean()
	{
		project.buildDir.deleteDir( )
	}
}
