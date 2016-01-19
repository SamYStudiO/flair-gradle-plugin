package flair.gradle.plugins

import flair.gradle.tasks.variantFactories.IVariantTaskFactory

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTaskPlugin
{
	public void addVariantTaskFactory( IVariantTaskFactory factory )

	public void updateVariantTasks()
}
