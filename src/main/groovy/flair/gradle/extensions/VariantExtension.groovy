package flair.gradle.extensions

import flair.gradle.utils.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class VariantExtension extends AbstractPlatformExtension implements IVariantExtension
{
	private String dimension

	VariantExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}

	String getDimension()
	{
		return dimension
	}

	void dimension( String dimension )
	{
		this.dimension = dimension
	}
}
