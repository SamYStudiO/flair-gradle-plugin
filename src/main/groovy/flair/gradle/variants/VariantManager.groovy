package flair.gradle.variants

import flair.gradle.extensions.configuration.IVariantConfigurationContainerExtension
import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.platforms.Platform
import flair.gradle.plugins.PluginManager
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public final class VariantManager
{
	public static List<String> getProductFlavors( Project project )
	{
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> iosProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.IOS ) ? PropertyManager.getRootContainer( project ).getPlatformContainer( Platform.IOS ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> androidProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ? PropertyManager.getRootContainer( project ).getPlatformContainer( Platform.ANDROID ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> desktopProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? PropertyManager.getRootContainer( project ).getPlatformContainer( Platform.DESKTOP ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonProductFlavors = PropertyManager.getRootContainer( project ).getProductFlavors( )

		List<String> list = new ArrayList<String>( )

		if( iosProductFlavors ) iosProductFlavors.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }
		if( androidProductFlavors ) androidProductFlavors.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }
		if( desktopProductFlavors ) desktopProductFlavors.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }
		commonProductFlavors.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }

		return list
	}

	public static List<String> getBuildTypes( Project project )
	{
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> iosBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.IOS ) ? PropertyManager.getRootContainer( project ).getPlatformContainer( Platform.IOS ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> androidBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ? PropertyManager.getRootContainer( project ).getPlatformContainer( Platform.ANDROID ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> desktopBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? PropertyManager.getRootContainer( project ).getPlatformContainer( Platform.DESKTOP ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonBuildTypes = PropertyManager.getRootContainer( project ).getBuildTypes( )

		List<String> list = new ArrayList<String>( )

		if( iosBuildTypes ) iosBuildTypes.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }
		if( androidBuildTypes ) androidBuildTypes.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }
		if( desktopBuildTypes ) desktopBuildTypes.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }
		commonBuildTypes.each { if( list.indexOf( it.name ) < 0 ) list.add( it.name ) }

		return list
	}

	public static List<Variant> getVariants( Project project )
	{
		List<Variant> list = new ArrayList<Variant>( )

		if( PluginManager.hasPlatformPlugin( project , Platform.IOS ) ) list.addAll( getVariants( project , Platform.IOS ) )
		if( PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ) list.addAll( getVariants( project , Platform.ANDROID ) )
		if( PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ) list.addAll( getVariants( project , Platform.DESKTOP ) )

		return list
	}

	public static List<Variant> getVariants( Project project , Platform platform )
	{
		if( !platform || !PluginManager.hasPlatformPlugin( project , platform ) ) return getVariants( project )

		List<Variant> list = new ArrayList<Variant>( )

		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> platformProductFlavors = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).getProductFlavors( )
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> platformBuildTypes = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).getBuildTypes( )

		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonProductFlavors = PropertyManager.getRootContainer( project ).getProductFlavors( )
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonBuildTypes = PropertyManager.getRootContainer( project ).getBuildTypes( )

		List<String> platformDimension = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).flavorDimensions
		List<String> commonDimension = PropertyManager.getRootContainer( project ).flavorDimensions

		List<IVariantConfigurationContainerExtension> mergedProductFlavors = platformProductFlavors.toList( )

		commonProductFlavors.each { flavor ->

			if( !platformProductFlavors.findByName( flavor.name ) ) mergedProductFlavors.add( flavor )
		}

		List<IVariantConfigurationContainerExtension> mergedBuildTypes = platformBuildTypes.toList( )

		commonBuildTypes.each { type ->

			if( !platformBuildTypes.findByName( type.name ) ) mergedBuildTypes.add( type )
		}

		List<String> mergedDimension = platformDimension && platformDimension.size( ) ? platformDimension : commonDimension

		// add flavors with no dimension
		if( mergedProductFlavors.size( ) )
		{
			mergedProductFlavors.each { flavor ->

				if( !mergedDimension || !mergedDimension.contains( flavor.dimension ) )
				{
					if( mergedBuildTypes.size( ) )
					{
						mergedBuildTypes.each { type -> list.add( new Variant( project , platform , flavor.name , type.name ) )
						}
					}
					else
					{
						list.add( new Variant( project , platform , flavor.name ) )
					}
				}
			}
		}
		else
		{
			mergedBuildTypes.each { type -> list.add( new Variant( project , platform , ( String ) null , type.name ) )
			}
		}


		// add flavors with dimension
		// TODO rough code could be done a proper way?
		List<List<IVariantConfigurationContainerExtension>> flavorDimensions = new ArrayList<>( )

		mergedDimension.each { dimension ->

			flavorDimensions.add( mergedProductFlavors.findAll( ) { it.dimension == dimension } )
		}

		int totalVariants = 0

		flavorDimensions.each { flavors ->

			totalVariants = totalVariants == 0 ? flavors.size( ) : totalVariants * flavors.size( )
		}

		List<String>[] flavorVariants = new List<String>[totalVariants]

		int flavorsIndex = 0
		int range = 0

		if( totalVariants > 0 )
		{
			flavorDimensions.each { flavors ->

				if( flavorVariants[ 0 ] == null )
				{
					int flavorsSize = flavors.size( )
					int flavorIndex = 0
					range = totalVariants / flavorsSize

					flavors.each { flavor ->

						int start = flavorIndex * range

						for( int i = start; i < start + range; i++ )
						{
							if( !flavorVariants[ i ] ) flavorVariants[ i ] = new ArrayList<String>( )

							flavorVariants[ i ].add( flavor.name )
						}

						flavorIndex++
					}
				}
				else if( flavors.size( ) > 0 )
				{
					range = range / flavors.size( )

					int cpt = 0

					for( int i = 0; i < totalVariants / range; i++ )
					{
						for( int j = 0; j < range; j++ )
						{
							flavorVariants[ cpt ].add( flavors[ i % flavors.size( ) ].name )

							cpt++
						}
					}
				}

				flavorsIndex++
			}

			if( mergedBuildTypes.size( ) )
			{
				mergedBuildTypes.each { type ->

					flavorVariants.each {

						list.add( new Variant( project , platform , it , type.name ) )
					}
				}
			}
			else
			{
				flavorVariants.each {

					list.add( new Variant( project , platform , it ) )
				}
			}
		}

		return list
	}
}
