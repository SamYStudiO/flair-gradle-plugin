package flair.gradle.extensions.configuration

import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantConfigurationContainerExtension extends ConfigurationContainerExtension implements IVariantConfigurationContainerExtension
{
	protected String dimension

	public VariantConfigurationContainerExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}

	public String getDimension()
	{
		return dimension
	}

	public void setDimension( String value )
	{
		dimension = value
	}
}
