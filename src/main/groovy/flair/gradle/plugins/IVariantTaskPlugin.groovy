package flair.gradle.plugins

import flair.gradle.tasks.variantFactories.IVariantTaskFactory

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTaskPlugin
{
	List<IVariantTaskFactory> getVariantTaskFactories()
}