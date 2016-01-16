package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import flair.gradle.tasks.TaskManager
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class VariantConfigurationContainerExtension extends ConfigurationContainerExtension implements IVariantConfigurationContainerExtension
{
	protected NamedDomainObjectContainer<IConfigurationContainerExtension> productFlavors

	protected NamedDomainObjectContainer<IConfigurationContainerExtension> buildTypes

	public VariantConfigurationContainerExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		productFlavors = project.container( ConfigurationContainerExtension ) {
			new ConfigurationContainerExtension( it , project , platform )
		}

		buildTypes = project.container( ConfigurationContainerExtension ) {
			new ConfigurationContainerExtension( it , project , platform )
		}

		productFlavors.whenObjectAdded {
			TaskManager.updateTasks( project )
		}

		buildTypes.whenObjectAdded {
			TaskManager.updateTasks( project )
		}
	}

	public void productFlavors( Action<? super NamedDomainObjectContainer<IConfigurationContainerExtension>> action )
	{
		action.execute( productFlavors )
	}

	public void buildTypes( Action<? super NamedDomainObjectContainer<IConfigurationContainerExtension>> action )
	{
		action.execute( buildTypes )
	}

	@Override
	public NamedDomainObjectContainer<IConfigurationContainerExtension> getProductFlavors()
	{
		return productFlavors
	}

	@Override
	public NamedDomainObjectContainer<IConfigurationContainerExtension> getBuildTypes()
	{
		return buildTypes
	}

	@Override
	public IConfigurationContainerExtension getProductFlavor( String name )
	{
		return productFlavors.getByName( name ) ?: new ConfigurationContainerExtension( name , project , platform )
	}

	@Override
	public IConfigurationContainerExtension getBuildType( String name )
	{
		return buildTypes.getByName( name ) ?: new ConfigurationContainerExtension( name , project , platform )
	}
}
