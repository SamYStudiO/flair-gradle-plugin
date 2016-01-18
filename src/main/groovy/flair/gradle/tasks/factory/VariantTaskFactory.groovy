package flair.gradle.tasks.factory

import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface VariantTaskFactory<T>
{
	public T create( Project project , Variant variant )
}