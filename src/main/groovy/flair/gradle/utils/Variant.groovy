package flair.gradle.utils

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

	public enum NamingType
	{
		CAPITALIZE( "" ) ,
		CAPITALIZE_BUT_FIRST( "" ) ,
		UNDERSCORE( "_" ) ,
		HYPHEN( "-" ) ,
		SPACE( " " )

		private String c

		public NamingType( String c )
		{
			this.c = c
		}

		public String getC()
		{
			return c
		}
	}

	public String getName()
	{
		return getName( NamingType.UNDERSCORE , true )
	}

	public String getName( NamingType type )
	{
		return getName( type , true )
	}

	public String getName( NamingType type , boolean withPlatform )
	{
		String name = ""

		switch( type )
		{
			case NamingType.CAPITALIZE:
			case NamingType.CAPITALIZE_BUT_FIRST:

				if( withPlatform ) name += platform.name.capitalize( )

				productFlavors.each { flavor -> name += flavor.capitalize( ) }

				if( buildType ) name += buildType.capitalize( )

				if( type == NamingType.CAPITALIZE_BUT_FIRST && name.length( ) ) name = name.substring( 0 , 1 ).toLowerCase( ) + name.substring( 1 )

				return name

			default:

				type = NamingType.UNDERSCORE;
				if( withPlatform ) name += platform.name.toLowerCase( ) + type.c
				productFlavors.each { flavor -> name += flavor + type.c }

				if( buildType ) name += buildType else if( name.length( ) ) name = name.substring( 0 , name.size( ) - 1 )

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
		getDirectories( NamingType.UNDERSCORE )
	}

	public List<String> getDirectories( NamingType type )
	{
		List<String> list = new ArrayList<String>( )
		list.add( type == NamingType.CAPITALIZE ? "Main" : "main" )
		list.add( type == NamingType.CAPITALIZE ? platform.name.capitalize( ) : platform.name )
		productFlavors.each { list.add( type == NamingType.CAPITALIZE ? it.capitalize( ) : it ) }
		if( buildType ) list.add( type == NamingType.CAPITALIZE ? buildType.capitalize( ) : buildType )

		switch( type )
		{
			case NamingType.CAPITALIZE:
			case NamingType.CAPITALIZE_BUT_FIRST:

				productFlavors.each {
					list.add( "${ type == NamingType.CAPITALIZE ? platform.name.capitalize( ) : platform.name }${ it.capitalize( ) }" )
				}
				if( productFlavors.size( ) > 1 )
				{
					String name = type == NamingType.CAPITALIZE ? platform.name.capitalize( ) : platform.name
					productFlavors.each {
						name += it.capitalize( )
					}

					list.add( name )
				}
				if( buildType ) list.add( "${ type == NamingType.CAPITALIZE ? platform.name.capitalize( ) : platform.name }${ buildType.capitalize( ) }" )
				if( productFlavors.size( ) > 0 ) list.add( getName( type ) )

				break

			default:

				type = NamingType.UNDERSCORE;
				productFlavors.each { list.add( "${ platform.name }${ type.c }${ it }" ) }
				if( productFlavors.size( ) > 1 ) list.add( "${ platform.name }${ type.c }${ productFlavors.join( type.c ) }" )
				if( buildType ) list.add( "${ platform.name }${ type.c }${ buildType }" )
				if( productFlavors.size( ) > 0 ) list.add( getName( type ) )
		}

		return list
	}
}
