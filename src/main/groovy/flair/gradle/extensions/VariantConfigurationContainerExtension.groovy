package flair.gradle.extensions

import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantConfigurationContainerExtension extends AbstractConfigurationContainerExtension implements IVariantConfigurationContainerExtension
{
	protected String dimension

	public VariantConfigurationContainerExtension( String name , Project project , Platforms platform )
	{
		super( name , project , platform )
	}

	public String getDimension()
	{
		return dimension
	}

	public void setDimension( String dimension )
	{
		this.dimension = dimension
	}
}
