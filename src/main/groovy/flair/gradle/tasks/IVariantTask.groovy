package flair.gradle.tasks

import flair.gradle.variants.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTask extends IFlairTask
{
	public Variant getVariant()

	public void setVariant( Variant variant )
}