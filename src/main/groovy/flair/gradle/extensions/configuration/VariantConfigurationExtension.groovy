package flair.gradle.extensions.configuration

import flair.gradle.extensions.configuration.variants.BuildTypeExtension
import flair.gradle.extensions.configuration.variants.ProductFlavorExtension
import flair.gradle.platforms.Platform
import flair.gradle.tasks.TaskManager
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

	public VariantConfigurationExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		this.buildTypes = project.container( BuildTypeExtension ) {
			new BuildTypeExtension( it , project )
		}
		this.productFlavors = project.container( ProductFlavorExtension ) {
			new ProductFlavorExtension( it , project )
		}

		buildTypes.whenObjectAdded { type -> TaskManager.updateVariantTasks( project , productFlavors , buildTypes )
		}

		productFlavors.whenObjectAdded { flavor -> TaskManager.updateVariantTasks( project , productFlavors , buildTypes )
		}
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
