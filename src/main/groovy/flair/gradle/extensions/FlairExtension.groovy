package flair.gradle.extensions

import flair.gradle.extensions.configuration.variants.BuildTypeExtension
import flair.gradle.extensions.configuration.VariantConfigurationExtension
import flair.gradle.extensions.configuration.variants.ProductFlavorExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtension extends VariantConfigurationExtension
{
	public static final NAME = "flair"

	public String moduleName = "app"

	public FlairExtension( String name , Project project , NamedDomainObjectContainer<BuildTypeExtension> buildTypes , NamedDomainObjectContainer<ProductFlavorExtension> productFlavors )
	{
		super( name , project , buildTypes , productFlavors )
	}
}
