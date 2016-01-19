package flair.gradle.extensions

import flair.gradle.variants.Platform
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PlatformConfigurationContainerExtension extends AbstractConfigurationContainerExtension implements IPlatformConfigurationContainerExtension
{
	protected NamedDomainObjectContainer<IVariantConfigurationContainerExtension> productFlavors

	protected NamedDomainObjectContainer<IVariantConfigurationContainerExtension> buildTypes

	protected List<String> flavorDimensions

	public PlatformConfigurationContainerExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		productFlavors = project.container( VariantConfigurationContainerExtension ) {
			new VariantConfigurationContainerExtension( it , project , platform )
		}

		buildTypes = project.container( VariantConfigurationContainerExtension ) {
			new VariantConfigurationContainerExtension( it , project , platform )
		}

		productFlavors.whenObjectAdded {
			//StructureManager.updateVariantDirectories( project )
			//TaskManager.updateTasks( project )
		}

		buildTypes.whenObjectAdded {
			//StructureManager.updateVariantDirectories( project )
			//TaskManager.updateTasks( project )
		}
	}

	public void productFlavors( Action<? super NamedDomainObjectContainer<IVariantConfigurationContainerExtension>> action )
	{
		action.execute( productFlavors )
	}

	public void buildTypes( Action<? super NamedDomainObjectContainer<IVariantConfigurationContainerExtension>> action )
	{
		action.execute( buildTypes )
	}

	@Override
	public List<String> getFlavorDimensions()
	{
		return flavorDimensions
	}

	@Override
	public void setFlavorDimensions( List<String> flavorDimensions )
	{
		this.flavorDimensions = flavorDimensions
	}

	@Override
	public void setFlavorDimensions( String... flavorDimensions )
	{
		this.flavorDimensions = flavorDimensions
	}

	@Override
	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getProductFlavors()
	{
		return productFlavors
	}

	@Override
	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getBuildTypes()
	{
		return buildTypes
	}

	@Override
	public IVariantConfigurationContainerExtension getProductFlavor( String name )
	{
		return productFlavors.findByName( name ) ? productFlavors.getByName( name ) : new VariantConfigurationContainerExtension( name , project , platform )
	}

	@Override
	public IVariantConfigurationContainerExtension getBuildType( String name )
	{
		return buildTypes.findByName( name ) ? buildTypes.getByName( name ) : new VariantConfigurationContainerExtension( name , project , platform )
	}
}
