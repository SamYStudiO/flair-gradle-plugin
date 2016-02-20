package flair.gradle.extensions

import flair.gradle.plugins.PluginManager
import flair.gradle.variants.Platform
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

	public FlairExtension( String name , Project project , Platform platform )
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
	public IPlatformContainerExtension getPlatformContainer( Platform platform )
	{
		return ( platform && PluginManager.hasPlatformPlugin( project , platform ) ? project.flair[ platform.name.toLowerCase( ) ] : project.flair ) as IPlatformContainerExtension
	}

	@Override
	public Object getProp( String property , Variant variant , boolean returnDefaultIfNull )
	{
		Object value = super.getProp( property , variant , returnDefaultIfNull )

		if( value || !returnDefaultIfNull ) return value else
		{
			switch( property )
			{
				case FlairProperty.MODULE_NAME.name: return "app"
				case FlairProperty.PACKAGE_NAME.name: return ""
				case FlairProperty.AUTO_GENERATE_VARIANT_DIRECTORIES.name: return true

				default: return null
			}
		}
	}

	@Override
	public List<String> getAllActivePlatformProductFlavors()
	{
		NamedDomainObjectContainer<IVariantExtension> iosProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.IOS ) ? getPlatformContainer( Platform.IOS ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantExtension> androidProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ? getPlatformContainer( Platform.ANDROID ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantExtension> desktopProductFlavors = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? getPlatformContainer( Platform.DESKTOP ).getProductFlavors( ) : null
		NamedDomainObjectContainer<IVariantExtension> commonProductFlavors = getProductFlavors( )

		List<String> list = new ArrayList<String>( )

		commonProductFlavors.each {
			list.add( it.name )
		}

		if( iosProductFlavors )
		{
			iosProductFlavors.each {

				if( list.indexOf( it.name ) < 0 )
				{
					list.add( it.name )
				}
			}
		}

		if( androidProductFlavors )
		{
			androidProductFlavors.each {

				if( list.indexOf( it.name ) < 0 )
				{
					list.add( it.name )
				}
			}
		}

		if( desktopProductFlavors )
		{
			desktopProductFlavors.each {

				if( list.indexOf( it.name ) < 0 )
				{
					list.add( it.name )
				}
			}
		}

		return list
	}

	@Override
	public List<String> getAllActivePlatformBuildTypes()
	{
		NamedDomainObjectContainer<IVariantExtension> iosBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.IOS ) ? getPlatformContainer( Platform.IOS ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantExtension> androidBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ? getPlatformContainer( Platform.ANDROID ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantExtension> desktopBuildTypes = PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ? getPlatformContainer( Platform.DESKTOP ).getBuildTypes( ) : null
		NamedDomainObjectContainer<IVariantExtension> commonBuildTypes = getBuildTypes( )

		List<String> list = new ArrayList<String>( )

		commonBuildTypes.each {
			list.add( it.name )
		}

		if( iosBuildTypes )
		{
			iosBuildTypes.each {

				if( list.indexOf( it.name ) < 0 )
				{
					list.add( it.name )
				}
			}
		}

		if( androidBuildTypes )
		{
			androidBuildTypes.each {

				if( list.indexOf( it.name ) < 0 )
				{
					list.add( it.name )
				}
			}
		}

		if( desktopBuildTypes )
		{
			desktopBuildTypes.each {

				if( list.indexOf( it.name ) < 0 )
				{
					list.add( it.name )
				}
			}
		}

		return list
	}

	@Override
	public List<Variant> getAllActivePlatformVariants()
	{
		List<Variant> list = new ArrayList<Variant>( )

		if( PluginManager.hasPlatformPlugin( project , Platform.IOS ) ) list.addAll( getPlatformVariants( Platform.IOS ) )
		if( PluginManager.hasPlatformPlugin( project , Platform.ANDROID ) ) list.addAll( getPlatformVariants( Platform.ANDROID ) )
		if( PluginManager.hasPlatformPlugin( project , Platform.DESKTOP ) ) list.addAll( getPlatformVariants( Platform.DESKTOP ) )

		return list
	}

	@Override
	public List<Variant> getPlatformVariants( Platform platform )
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

		if( list.size( ) == 0 ) list.add( new Variant( project , platform ) )

		return list
	}

	@Override
	public Object getFlairProperty( FlairProperty property )
	{
		getFlairProperty( ( Variant ) null , property )
	}

	@Override
	public Object getFlairProperty( Platform platform , FlairProperty property )
	{
		getFlairProperty( new Variant( project , platform ) , property )
	}

	@Override
	public Object getFlairProperty( String extensionName , FlairProperty property )
	{
		getFlairProperty( extensionName , ( Variant ) null , property )
	}

	@Override
	public Object getFlairProperty( String extensionName , Platform platform , FlairProperty property )
	{
		getFlairProperty( extensionName , new Variant( project , platform ) , property )
	}

	@Override
	public Object getFlairProperty( Variant variant , FlairProperty property )
	{
		Object value = null
		List list = null

		if( variant && variant.platform && variant.buildType ) value = getPlatformContainer( variant.platform ).getBuildType( variant.buildType ).getProp( property.name , variant )
		if( value instanceof List ) list = value
		else if( value != null ) return value

		if( variant && variant.buildType ) value = getBuildType( variant.buildType ).getProp( property.name , variant )
		if( value instanceof List )
		{
			if( list ) list.addAll( value )
			else list = value
		}
		else if( value != null ) return value

		if( variant && variant.platform && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getPlatformContainer( variant.platform ).getProductFlavor( variant.productFlavors[ i ] ).getProp( property.name , variant )
				if( value instanceof List )
				{
					if( list ) list.addAll( value )
					else list = value
				}
				else if( value != null ) return value
			}
		}

		if( variant && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getProductFlavor( variant.productFlavors[ i ] ).getProp( property.name , variant )
				if( value instanceof List )
				{
					if( list ) list.addAll( value )
					else list = value
				}
				else if( value != null ) return value
			}
		}

		if( variant && variant.platform ) value = getPlatformContainer( variant.platform ).getProp( property.name , variant )
		if( value instanceof List )
		{
			if( list ) list.addAll( value )
			else list = value
		}
		else if( value != null ) return value

		value = getProp( property.name , variant )
		if( value instanceof List )
		{
			if( list ) list.addAll( value )
			else list = value

			if( list.size(  ) ) return list
		}
		else if( value != null ) return value

		return getPlatformContainer( variant ? variant.platform : null ).getProp( property.name , variant , true )
	}

	@Override
	public Object getFlairProperty( String extensionName , Variant variant , FlairProperty property )
	{
		Object value = null
		List list = null

		if( variant && variant.platform && variant.buildType ) value = getPlatformContainer( variant.platform ).getBuildType( variant.buildType ).getExtension( extensionName ).getProp( property.name , variant )
		if( value instanceof List ) list = value
		else if( value != null ) return value

		if( variant && variant.buildType ) value = getBuildType( variant.buildType ).getExtension( extensionName ).getProp( property.name , variant )
		if( value instanceof List )
		{
			if( list ) list.addAll( value )
			else list = value
		}
		else if( value != null ) return value

		if( variant && variant.platform && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getPlatformContainer( platform ).getProductFlavor( variant.productFlavors[ i ] ).getExtension( extensionName ).getProp( property.name , variant )
				if( value instanceof List )
				{
					if( list ) list.addAll( value )
					else list = value
				}
				else if( value != null ) return value
			}
		}

		if( variant && variant.productFlavors )
		{
			for( int i = variant.productFlavors.size( ) - 1; i >= 0; i-- )
			{
				value = getProductFlavor( variant.productFlavors[ i ] ).getExtension( extensionName ).getProp( property.name , variant )
				if( value instanceof List )
				{
					if( list ) list.addAll( value )
					else list = value
				}
				else if( value != null ) return value
			}
		}

		if( variant && variant.platform ) value = getPlatformContainer( variant.platform ).getExtension( extensionName ).getProp( property.name , variant )
		if( value instanceof List )
		{
			if( list ) list.addAll( value )
			else list = value
		}
		else if( value != null ) return value

		value = getExtension( extensionName ).getProp( property.name , variant )
		if( value instanceof List )
		{
			if( list ) list.addAll( value )
			else list = value

			if( list.size(  ) ) return list
		}
		else if( value != null ) return value

		return getPlatformContainer( variant ? variant.platform : null ).getExtension( extensionName ).getProp( property.name , variant , true )
	}
}
