package flair.gradle.tasks

import flair.gradle.variants.Variant
import org.gradle.api.DefaultTask

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AbstractVariantTask extends DefaultTask implements IVariantTask
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
