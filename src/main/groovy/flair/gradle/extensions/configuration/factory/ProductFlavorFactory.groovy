package flair.gradle.extensions.configuration.factory;

import flair.gradle.extensions.configuration.variants.ProductFlavorExtension;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProductFlavorFactory implements NamedDomainObjectFactory< ProductFlavorExtension >
{
	private final Project project

	public ProductFlavorFactory( Project project )
	{
		this.project = project
	}

	@Override
	public ProductFlavorExtension create( String name )
	{
		return new ProductFlavorExtension( name, project )
	}
}
