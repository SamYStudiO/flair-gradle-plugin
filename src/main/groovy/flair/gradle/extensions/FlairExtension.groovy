package flair.gradle.extensions

import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platform
import flair.gradle.variants.Variant
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtension extends PlatformConfigurationContainerExtension implements IPlatformVariantConfigurationContainerExtension , IPlatformExtensionManager
{
	public static final NAME = "flair"

	protected Watcher watcher

	public String moduleName

	public String packageName

	public Boolean autoGenerateVariantDirectories

	public FlairExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		watcher = new Watcher( project.rootDir )
		Thread t = new Thread( watcher )
		t.start( )
	}

	public Watcher getWatcher()
	{
		return watcher
	}

	@Override
	public IPlatformConfigurationContainerExtension getPlatformContainer( Platform platform )
	{
		return ( platform && PluginManager.hasPlatformPlugin( project , platform ) ? project.flair[ platform.name.toLowerCase( ) ] : project.flair ) as IPlatformConfigurationContainerExtension
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		Object value = super.getProp( property , returnDefaultIfNull )

		if( value || !returnDefaultIfNull ) return value else
		{
			switch( property )
			{
				case "moduleName": return "app"
				case "packageName": return ""
				case "autoGenerateVariantDirectories": return true

				default: return null
			}
		}
	}

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getAllProductFlavors()
	{
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> iosProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.IOS ) ? getPlatformContainer( Platform.IOS ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> androidProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ? getPlatformContainer( Platform.ANDROID ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> desktopProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? getPlatformContainer( Platform.DESKTOP ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonProductFlavors = getProductFlavors( )

		if( iosProductFlavors ) commonProductFlavors.addAll( iosProductFlavors )
		if( androidProductFlavors ) commonProductFlavors.addAll( androidProductFlavors )
		if( desktopProductFlavors ) commonProductFlavors.addAll( desktopProductFlavors )

		return commonProductFlavors
	}

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getAllBuildTypes()
	{
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> iosBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.IOS ) ? getPlatformContainer( Platform.IOS ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> androidBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ? getPlatformContainer( Platform.ANDROID ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> desktopBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? getPlatformContainer( Platform.DESKTOP ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonBuildTypes = getBuildTypes( )

		if( iosBuildTypes ) commonBuildTypes.addAll( iosBuildTypes )
		if( androidBuildTypes ) commonBuildTypes.addAll( androidBuildTypes )
		if( desktopBuildTypes ) commonBuildTypes.addAll( desktopBuildTypes )

		return commonBuildTypes
	}

	public List<Variant> getAllVariants()
	{
		List<Variant> list = new ArrayList<Variant>( )

		if( PluginManager.hasPlatformPlugin( project , Platform.IOS ) ) list.addAll( getAllVariants( Platform.IOS ) )
		if( PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ) list.addAll( getAllVariants( Platform.ANDROID ) )
		if( PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ) list.addAll( getAllVariants( Platform.DESKTOP ) )

		return list
	}

	public List<Variant> getAllVariants( Platform platform )
	{
		if( !platform || !PluginManager.hasPlatformPlugin( project , platform ) ) return getAllVariants( )

		List<Variant> list = new ArrayList<Variant>( )

		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> platformProductFlavors = getPlatformContainer( platform ).getProductFlavors( )
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> platformBuildTypes = getPlatformContainer( platform ).getBuildTypes( )

		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonProductFlavors = getProductFlavors( )
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> commonBuildTypes = getBuildTypes( )

		List<String> platformDimension = getPlatformContainer( platform ).flavorDimensions
		List<String> commonDimension = flavorDimensions

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
		// TODO rough code! could be done a proper way?
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

	public Object getFlairProperty( String name )
	{
		getFlairProperty( name , ( Variant ) null )
	}

	public Object getFlairProperty( String name , Platform platform )
	{
		getFlairProperty( name , new Variant( project , platform ) )
	}

	public Object getFlairProperty( String configurationName , String name )
	{
		getFlairProperty( configurationName , name , ( Variant ) null )
	}

	public Object getFlairProperty( String configurationName , String name , Platform platform )
	{
		getFlairProperty( configurationName , name , new Variant( project , platform ) )
	}

	public Object getFlairProperty( String name , Variant variant )
	{
		Object value

		if( variant && variant.platform && variant.buildType ) value = getPlatformContainer( variant.platform ).getBuildType( variant.buildType ).getProp( name )
		if( value ) return value

		if( variant && variant.buildType ) value = getBuildType( variant.buildType ).getProp( name )
		if( value ) return value

		if( variant && variant.platform && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getPlatformContainer( variant.platform ).getProductFlavor( variant.productFlavors[ i ] ).getProp( name )
				if( value ) return value
			}
		}

		if( variant && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getProductFlavor( variant.productFlavors[ i ] ).getProp( name )
				if( value ) return value
			}
		}

		if( variant && variant.platform ) value = getPlatformContainer( variant.platform ).getProp( name )
		if( value ) return value

		value = getProp( name )
		if( value ) return value

		return getPlatformContainer( variant ? variant.platform : null ).getProp( name , true )
	}

	public Object getFlairProperty( String configurationName , String name , Variant variant )
	{
		Object value

		if( variant && variant.platform && variant.buildType ) value = getPlatformContainer( variant.platform ).getBuildType( variant.buildType ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		if( variant && variant.buildType ) value = getBuildType( variant.buildType ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		if( variant && variant.platform && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getPlatformContainer( platform ).getProductFlavor( variant.productFlavors[ i ] ).getConfiguration( configurationName ).getProp( name )
				if( value ) return value
			}
		}

		if( variant && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getProductFlavor( variant.productFlavors[ i ] ).getConfiguration( configurationName ).getProp( name )
				if( value ) return value
			}
		}

		if( variant && variant.platform ) value = getPlatformContainer( variant.platform ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		return getPlatformContainer( variant ? variant.platform : null ).getConfiguration( configurationName ).getProp( name , true )
	}
}
