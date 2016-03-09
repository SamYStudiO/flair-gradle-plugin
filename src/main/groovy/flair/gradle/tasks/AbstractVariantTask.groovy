package flair.gradle.tasks

import flair.gradle.variants.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractVariantTask extends AbstractTask implements IVariantTask
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

	protected File getOutputVariantDir()
	{
		return project.file( "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingType.UNDERSCORE ) }" )
	}
}
