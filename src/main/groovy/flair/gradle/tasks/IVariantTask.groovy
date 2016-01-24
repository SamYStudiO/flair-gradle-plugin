package flair.gradle.tasks

import flair.gradle.variants.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IVariantTask extends ITask
{
	Variant getVariant()

	void setVariant( Variant variant )
}