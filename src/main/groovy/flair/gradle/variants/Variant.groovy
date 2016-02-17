package flair.gradle.variants

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public final class Variant
{
	private Project project

	private Platform platform

	private List<String> productFlavors

	private String buildType

	public Variant( Project project , Platform platform )
	{
		this( project , platform , ( String ) null )
	}

	public Variant( Project project , Platform platform , String flavor )
	{
		this( project , platform , flavor , null )
	}

	public Variant( Project project , Platform platform , String flavor , String type )
	{
		this( project , platform , flavor ? [ flavor ] : null , type )
	}

	public Variant( Project project , Platform platform , List<String> flavors )
	{
		this( project , platform , flavors , null )
	}

	public Variant( Project project , Platform platform , List<String> flavors , String type )
	{
		this.project = project
		this.platform = platform
		productFlavors = flavors ?: new ArrayList<String>( )
		buildType = type
	}

	public String getName()
	{
		String name = ""

		name += platform.name
		productFlavors.each { flavor -> name += flavor }

		if( buildType ) name += buildType

		return name
	}

	public enum NamingTypes
	{
		CAPITALIZE( "" ) ,
		CAPITALIZE_BUT_FIRST( "" ) ,
		UNDERSCORE( "_" ) ,
		HYPHEN( "-" ) ,
		SPACE( " " )

		private String c

		public NamingTypes( String c )
		{
			this.c = c
		}

		public String getC()
		{
			return c
		}
	}

	public String getNameWithType( NamingTypes type )
	{
		String name = ""

		switch( type )
		{
			case NamingTypes.CAPITALIZE:
			case NamingTypes.CAPITALIZE_BUT_FIRST:
				name += platform.name.capitalize( )
				productFlavors.each { flavor -> name += flavor.capitalize( ) }

				if( buildType ) name += buildType.capitalize( )

				if( type == NamingTypes.CAPITALIZE_BUT_FIRST ) name = name.substring( 0 , 1 ).toLowerCase( ) + name.substring( 1 )

				return name

			default:

				name += platform.name.toLowerCase( ) + type.c
				productFlavors.each { flavor -> name += flavor + type.c }

				if( buildType ) name += buildType else name = name.substring( 0 , name.size( ) - 1 )

				return name
		}
	}

	public String getProductFlavorsBuildTypeWithType( NamingTypes type )
	{
		String name = ""

		switch( type )
		{
			case NamingTypes.CAPITALIZE:
			case NamingTypes.CAPITALIZE_BUT_FIRST:
				productFlavors.each { flavor -> name += flavor.capitalize( ) }

				if( buildType ) name += buildType.capitalize( )

				if( type == NamingTypes.CAPITALIZE_BUT_FIRST ) name = name.substring( 0 , 1 ).toLowerCase( ) + name.substring( 1 )

				return name

			default:
				productFlavors.each { flavor -> name += flavor + type.c }

				if( buildType ) name += buildType else name = name.substring( 0 , name.size( ) - 1 )

				return name
		}
	}

	public Platform getPlatform()
	{
		return platform
	}

	public setPlatform( Platform platform )
	{
		this.platform = platform
	}

	public List<String> getProductFlavors()
	{
		return productFlavors
	}

	public setProductFlavors( List<String> productFlavors )
	{
		this.productFlavors = productFlavors
	}

	public String getBuildType()
	{
		return buildType
	}

	public setBuildType( String buildType )
	{
		this.buildType = buildType
	}

	public List<String> getDirectories()
	{
		List<String> list = new ArrayList<String>( )
		list.add( "main" )
		list.add( platform.name )
		productFlavors.each { list.add( it ) }
		if( buildType ) list.add( buildType )
		productFlavors.each { list.add( "${ platform.name }_${ it }" ) }
		if( productFlavors.size( ) > 1 ) list.add( "${ platform.name }_${ productFlavors.join( "_" ) }" )
		if( buildType ) list.add( "${ platform.name }_${ buildType }" )
		if( productFlavors.size( ) > 0 ) list.add( getNameWithType( NamingTypes.UNDERSCORE ) )

		return list
	}

	public List<String> getDirectoriesCapitalized()
	{
		List<String> list = new ArrayList<String>( )
		list.add( "main" )
		list.add( platform.name )
		productFlavors.each { list.add( it ) }
		if( buildType ) list.add( buildType )
		productFlavors.each { list.add( "${ platform.name }${ it.capitalize( ) }" ) }
		if( productFlavors.size( ) > 1 )
		{
			String name = platform.name
			productFlavors.each {
				name += it.capitalize( )
			}

			list.add( name )
		}
		if( buildType ) list.add( "${ platform.name }${ buildType.capitalize( ) }" )
		if( productFlavors.size( ) > 0 ) list.add( getNameWithType( NamingTypes.CAPITALIZE_BUT_FIRST ) )

		return list
	}
}
