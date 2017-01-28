package flair.gradle.tasks

import flair.gradle.utils.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class VariantTask extends AbstractTask implements IVariantTask
{
	protected Variant variant

	Variant getVariant()
	{
		return variant
	}

	void setVariant( Variant variant )
	{
		this.variant = variant
	}

	protected File getOutputVariantDir()
	{
		return project.file( "${ project.buildDir.path }/${ variant.name }" )
	}
}
