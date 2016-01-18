package flair.gradle.plugins

import flair.gradle.tasks.variantFactories.IVariantTaskFactory
import flair.gradle.variants.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformPlugin
{
	public Platform getPlatform()

	public void setPlatform( Platform platform )

	public void addVariantTaskFactory( IVariantTaskFactory factory )

	public void updateVariantTasks()
}
