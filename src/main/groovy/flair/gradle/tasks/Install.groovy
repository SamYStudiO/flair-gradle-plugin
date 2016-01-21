package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Install extends AbstractVariantTask
{
	public Install()
	{
		group = Groups.INSTALL.name
		description = ""
	}

	@TaskAction
	public void install()
	{
	}
}
