package flair.gradle.tasks.variantFactories

import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTaskFactory<T>
{
	public T create( Project project , Variant variant )
}