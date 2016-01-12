package flair.gradle.extensions.configuration

import flair.gradle.extensions.configuration.variants.BuildTypeExtension
import flair.gradle.extensions.configuration.variants.ProductFlavorExtension
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantConfigurationExtension extends ConfigurationExtension
{
	protected NamedDomainObjectContainer<BuildTypeExtension> buildTypes

	protected NamedDomainObjectContainer<ProductFlavorExtension> productFlavors

	public VariantConfigurationExtension( String name , Project project , NamedDomainObjectContainer<BuildTypeExtension> buildTypes = null , NamedDomainObjectContainer<ProductFlavorExtension> productFlavors = null )
	{
		super( name , project )

		this.buildTypes = buildTypes
		this.productFlavors = productFlavors
	}

	public void buildTypes( Action<? super NamedDomainObjectContainer<BuildTypeExtension>> action )
	{
		action.execute( buildTypes )
	}

	public void productFlavors( Action<? super NamedDomainObjectContainer<ProductFlavorExtension>> action )
	{
		action.execute( productFlavors )
	}
}
