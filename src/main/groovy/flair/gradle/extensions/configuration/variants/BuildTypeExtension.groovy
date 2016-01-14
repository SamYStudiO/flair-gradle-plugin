package flair.gradle.extensions.configuration.variants

import flair.gradle.extensions.configuration.AbstractExtension
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class BuildTypeExtension extends AbstractExtension implements IVariantExtension
{
	public BuildTypeExtension( String name , Project project )
	{
		super( name , project )
	}
}
