package flair.gradle.tasks

import flair.gradle.extensions.FlairProperties
import flair.gradle.variants.Variant

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AbstractVariantTask extends AbstractTask implements IVariantTask
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

	public File getModuleDir()
	{
		return project.file( extensionManager.getFlairProperty( FlairProperties.MODULE_NAME.name ) )
	}

	public File getOutputVariantDir()
	{
		return project.file( "${ project.buildDir.path }/${ variant.getNameWithType( Variant.NamingTypes.UNDERSCORE ) }" )
	}
}
