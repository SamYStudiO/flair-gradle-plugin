package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantContainer extends AbstractVariantTask
{
	public VariantContainer()
	{
		group = TaskGroup.BUILD.name
		description = ""
	}

	@TaskAction
	public void assemble()
	{
	}
}