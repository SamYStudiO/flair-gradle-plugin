package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Assemble extends AbstractVariantTask
{
	//protected String outputDir

	public Assemble()
	{
		group = Groups.BUILD.name
		description = ""
	}

	@TaskAction
	public void assemble()
	{
	}
}