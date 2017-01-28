package flair.gradle.extensions

import flair.gradle.dependencies.Config
import flair.gradle.utils.Platform
import flair.gradle.utils.Variant
import org.gradle.api.Action
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PlatformContainerExtension extends AbstractPlatformExtension implements IPlatformContainerExtension
{
	private NamedDomainObjectContainer<IVariantExtension> productFlavors

	private NamedDomainObjectContainer<IVariantExtension> buildTypes

	private List<String> flavorDimensions

	PlatformContainerExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		productFlavors = project.container( VariantExtension ) {
			new VariantExtension( it , project , platform )
		}

		buildTypes = project.container( VariantExtension ) {
			new VariantExtension( it , project , platform )
		}

		productFlavors.whenObjectAdded {
			updateConfigurations( )
		}

		buildTypes.whenObjectAdded {
			updateConfigurations( )
		}
	}

	void productFlavors( Action<? super NamedDomainObjectContainer<IVariantExtension>> action )
	{
		action.execute( productFlavors )
	}

	void buildTypes( Action<? super NamedDomainObjectContainer<IVariantExtension>> action )
	{
		action.execute( buildTypes )
	}

	@Override
	List<String> getFlavorDimensions()
	{
		return flavorDimensions
	}

	@Override
	void flavorDimensions( List<String> flavorDimensions )
	{
		this.flavorDimensions = flavorDimensions
	}

	@Override
	void flavorDimensions( String... flavorDimensions )
	{
		this.flavorDimensions = flavorDimensions
	}

	@Override
	NamedDomainObjectContainer<IVariantExtension> getProductFlavors()
	{
		return productFlavors
	}

	@Override
	NamedDomainObjectContainer<IVariantExtension> getBuildTypes()
	{
		return buildTypes
	}

	@Override
	IVariantExtension getProductFlavor( String name )
	{
		return productFlavors.findByName( name ) ? productFlavors.getByName( name ) : new VariantExtension( name , project , platform )
	}

	@Override
	IVariantExtension getBuildType( String name )
	{
		return buildTypes.findByName( name ) ? buildTypes.getByName( name ) : new VariantExtension( name , project , platform )
	}

	protected updateConfigurations()
	{
		Config.DEFAULTS.each { conf ->

			extensionManager.allActivePlatformVariants.each {

				it.getDirectories( Variant.NamingType.CAPITALIZE_BUT_FIRST ).each { directory ->

					String s = directory + conf.name.capitalize( )

					if( directory != "main" && !project.configurations.findByName( s ) )
					{
						Configuration c = project.configurations.create( s )
						String underscoredName = directory.replaceAll( /([A-Z])/ , /_$1/ ).toLowerCase( )

						switch( conf )
						{
							case Config.SOURCE:
								project.dependencies.add( c.name , project.files( "${ extensionManager.getFlairProperty( FlairProperty.MODULE_NAME ) }/src/${ underscoredName }/actionscript" ) )
								project.dependencies.add( c.name , project.files( "${ extensionManager.getFlairProperty( FlairProperty.MODULE_NAME ) }/src/${ underscoredName }/fonts" ) )
								break

							case Config.PACKAGE:
								project.dependencies.add( c.name , project.files( "${ extensionManager.getFlairProperty( FlairProperty.MODULE_NAME ) }/src/${ underscoredName }/assets" ) )
								break

							default: break
						}
					}
				}
			}
		}
	}
}
