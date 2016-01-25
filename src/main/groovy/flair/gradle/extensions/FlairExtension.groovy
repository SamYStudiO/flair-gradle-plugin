package flair.gradle.extensions

import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtension extends PlatformContainerExtension implements IExtensionManager
{
	private String moduleName

	private String packageName

	private Boolean autoGenerateVariantDirectories

	public FlairExtension( String name , Project project , Platforms platform )
	{
		super( name , project , platform )
	}

	public String getModuleName()
	{
		return moduleName
	}

	public void moduleName( String moduleName )
	{
		this.moduleName = moduleName
	}

	public String getPackageName()
	{
		return packageName
	}

	public void packageName( String packageName )
	{
		this.packageName = packageName
	}

	public Boolean getAutoGenerateVariantDirectories()
	{
		return autoGenerateVariantDirectories
	}

	public void autoGenerateVariantDirectories( Boolean autoGenerateVariantDirectories )
	{
		this.autoGenerateVariantDirectories = autoGenerateVariantDirectories
	}

	@Override
	public IPlatformContainerExtension getPlatformContainer( Platforms platform )
	{
		return ( platform && PluginManager.hasPlatformPlugin( project , platform ) ? project.flair[ platform.name.toLowerCase( ) ] : project.flair ) as IPlatformContainerExtension
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		Object value = super.getProp( property , returnDefaultIfNull )

		if( value || !returnDefaultIfNull ) return value else
		{
			switch( property )
			{
				case FlairProperties.MODULE_NAME.name: return "app"
				case FlairProperties.PACKAGE_NAME.name: return ""
				case FlairProperties.AUTO_GENERATE_VARIANT_DIRECTORIES.name: return true

				default: return null
			}
		}
	}

	public NamedDomainObjectContainer<IVariantExtension> getAllActivePlatformProductFlavors()
	{
		NamedDomainObjectContainer<IVariantExtension> iosProductFlavors = PluginManager.hasPlatformPlugin( project , Platforms.IOS ) ? getPlatformContainer( Platforms.IOS ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantExtension> androidProductFlavors = PluginManager.hasPlatformPlugin( project , Platforms.ANDROID ) ? getPlatformContainer( Platforms.ANDROID ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantExtension> desktopProductFlavors = PluginManager.hasPlatformPlugin( project , Platforms.DESKTOP ) ? getPlatformContainer( Platforms.DESKTOP ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantExtension> commonProductFlavors = getProductFlavors( )

		if( iosProductFlavors ) commonProductFlavors.addAll( iosProductFlavors )
		if( androidProductFlavors ) commonProductFlavors.addAll( androidProductFlavors )
		if( desktopProductFlavors ) commonProductFlavors.addAll( desktopProductFlavors )

		return commonProductFlavors
	}

	public NamedDomainObjectContainer<IVariantExtension> getAllActivePlatformBuildTypes()
	{
		NamedDomainObjectContainer<IVariantExtension> iosBuildTypes = PluginManager.hasPlatformPlugin( project , Platforms.IOS ) ? getPlatformContainer( Platforms.IOS ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantExtension> androidBuildTypes = PluginManager.hasPlatformPlugin( project , Platforms.ANDROID ) ? getPlatformContainer( Platforms.ANDROID ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantExtension> desktopBuildTypes = PluginManager.hasPlatformPlugin( project , Platforms.DESKTOP ) ? getPlatformContainer( Platforms.DESKTOP ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantExtension> commonBuildTypes = getBuildTypes( )

		if( iosBuildTypes ) commonBuildTypes.addAll( iosBuildTypes )
		if( androidBuildTypes ) commonBuildTypes.addAll( androidBuildTypes )
		if( desktopBuildTypes ) commonBuildTypes.addAll( desktopBuildTypes )

		return commonBuildTypes
	}

	public List<Variant> getAllActivePlatformVariants()
	{
		List<Variant> list = new ArrayList<Variant>( )

		if( PluginManager.hasPlatformPlugin( project , Platforms.IOS ) ) list.addAll( getPlatformVariants( Platforms.IOS ) )
		if( PluginManager.hasPlatformPlugin( project , Platforms.ANDROID ) ) list.addAll( getPlatformVariants( Platforms.ANDROID ) )
		if( PluginManager.hasPlatformPlugin( project , Platforms.DESKTOP ) ) list.addAll( getPlatformVariants( Platforms.DESKTOP ) )

		return list
	}

	public List<Variant> getPlatformVariants( Platforms platform )
	{
		if( !platform ) return getAllActivePlatformVariants( )

		List<Variant> list = new ArrayList<Variant>( )

		NamedDomainObjectContainer<IVariantExtension> platformProductFlavors = getPlatformContainer( platform ).getProductFlavors( )
		NamedDomainObjectContainer<IVariantExtension> platformBuildTypes = getPlatformContainer( platform ).getBuildTypes( )

		NamedDomainObjectContainer<IVariantExtension> commonProductFlavors = getProductFlavors( )
		NamedDomainObjectContainer<IVariantExtension> commonBuildTypes = getBuildTypes( )

		List<String> platformDimension = getPlatformContainer( platform ).flavorDimensions
		List<String> commonDimension = flavorDimensions

		List<IVariantExtension> mergedProductFlavors = platformProductFlavors.toList( )

		commonProductFlavors.each { flavor ->

			if( !platformProductFlavors.findByName( flavor.name ) ) mergedProductFlavors.add( flavor )
		}

		List<IVariantExtension> mergedBuildTypes = platformBuildTypes.toList( )

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
		List<List<IVariantExtension>> flavorDimensions = new ArrayList<>( )

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
		getFlairProperty( ( Variant ) null , name )
	}

	public Object getFlairProperty( Platforms platform , String name )
	{
		getFlairProperty( new Variant( project , platform ) , name )
	}

	public Object getFlairProperty( String extensionName , String name )
	{
		getFlairProperty( extensionName , ( Variant ) null , name )
	}

	public Object getFlairProperty( String extensionName , Platforms platform , String name )
	{
		getFlairProperty( extensionName , new Variant( project , platform ) , name )
	}

	public Object getFlairProperty( Variant variant , String name )
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

	public Object getFlairProperty( String extensionName , Variant variant , String name )
	{
		Object value

		if( variant && variant.platform && variant.buildType ) value = getPlatformContainer( variant.platform ).getBuildType( variant.buildType ).getExtension( extensionName ).getProp( name )
		if( value ) return value

		if( variant && variant.buildType ) value = getBuildType( variant.buildType ).getExtension( extensionName ).getProp( name )
		if( value ) return value

		if( variant && variant.platform && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getPlatformContainer( platform ).getProductFlavor( variant.productFlavors[ i ] ).getExtension( extensionName ).getProp( name )
				if( value ) return value
			}
		}

		if( variant && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getProductFlavor( variant.productFlavors[ i ] ).getExtension( extensionName ).getProp( name )
				if( value ) return value
			}
		}

		if( variant && variant.platform ) value = getPlatformContainer( variant.platform ).getExtension( extensionName ).getProp( name )
		if( value ) return value

		value = getExtension( extensionName ).getProp( name )
		if( value ) return value

		return getPlatformContainer( variant ? variant.platform : null ).getExtension( extensionName ).getProp( name , true )
	}
}
