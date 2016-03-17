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
	}

	@TaskAction
	public void assemble()
	{
	}
}