package flair.gradle.extensions

import flair.gradle.dependencies.Configurations
import flair.gradle.variants.Platforms
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PlatformContainerExtension extends AbstractPlatformExtension implements IPlatformContainerExtension
{
	private NamedDomainObjectContainer<IVariantExtension> productFlavors

	private NamedDomainObjectContainer<IVariantExtension> buildTypes

	private List<String> flavorDimensions

	public PlatformContainerExtension( String name , Project project , Platforms platform )
	{
		super( name , project , platform )

		productFlavors = project.container( VariantExtension ) {
			new VariantExtension( it , project , platform )
		}

		buildTypes = project.container( VariantExtension ) {
			new VariantExtension( it , project , platform )
		}

		productFlavors.whenObjectAdded {
			addConfiguration( it.name )
		}

		buildTypes.whenObjectAdded {
			addConfiguration( it.name )
		}
	}

	public void productFlavors( Action<? super NamedDomainObjectContainer<IVariantExtension>> action )
	{
		action.execute( productFlavors )
	}

	public void buildTypes( Action<? super NamedDomainObjectContainer<IVariantExtension>> action )
	{
		action.execute( buildTypes )
	}

	@Override
	public List<String> getFlavorDimensions()
	{
		return flavorDimensions
	}

	@Override
	public void flavorDimensions( List<String> flavorDimensions )
	{
		this.flavorDimensions = flavorDimensions
	}

	@Override
	public void flavorDimensions( String... flavorDimensions )
	{
		this.flavorDimensions = flavorDimensions
	}

	@Override
	public NamedDomainObjectContainer<IVariantExtension> getProductFlavors()
	{
		return productFlavors
	}

	@Override
	public NamedDomainObjectContainer<IVariantExtension> getBuildTypes()
	{
		return buildTypes
	}

	@Override
	public IVariantExtension getProductFlavor( String name )
	{
		return productFlavors.findByName( name ) ? productFlavors.getByName( name ) : new VariantExtension( name , project , platform )
	}

	@Override
	public IVariantExtension getBuildType( String name )
	{
		return buildTypes.findByName( name ) ? buildTypes.getByName( name ) : new VariantExtension( name , project , platform )
	}

	protected addConfiguration( String name )
	{
		Configurations.DEFAULTS.each { conf ->

			String s = name + conf.name.capitalize( )

			if( !project.configurations.findByName( s ) )
			{
				project.configurations.create( s )
			}
		}
	}
}
