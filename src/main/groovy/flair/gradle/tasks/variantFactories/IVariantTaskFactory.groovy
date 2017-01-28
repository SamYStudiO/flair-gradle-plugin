package flair.gradle.tasks.variantFactories

import flair.gradle.tasks.IVariantTask
import flair.gradle.utils.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTaskFactory<T extends IVariantTask>
{
	T create( Project project , Variant variant )
}