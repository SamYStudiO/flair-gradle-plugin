package flair.gradle.tasks

import flair.gradle.utils.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IVariantTask extends ITask
{
	Variant getVariant()

	void setVariant( Variant variant )
}