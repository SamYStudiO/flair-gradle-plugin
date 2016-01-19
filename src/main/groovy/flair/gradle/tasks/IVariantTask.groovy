package flair.gradle.tasks

import flair.gradle.variants.Variant
import org.gradle.api.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTask extends IFlairTask
{
	public Variant getVariant()

	public void setVariant( Variant variant )
}