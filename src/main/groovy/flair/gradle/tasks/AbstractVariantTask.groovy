package flair.gradle.tasks

import flair.gradle.variants.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AbstractVariantTask extends AbstractFlairTask implements IVariantTask
{
	protected Variant variant

	public Variant getVariant()
	{
		return variant
	}

	public void setVariant( Variant variant )
	{
		this.variant = variant
	}
}
