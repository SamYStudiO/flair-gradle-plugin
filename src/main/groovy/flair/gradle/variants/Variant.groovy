package flair.gradle.variants

import flair.gradle.plugins.PluginManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public final class Variant
{
	private Project project

	private Platforms platform

	private List<String> productFlavors

	private String buildType

	public Variant( Project project , Platforms platform )
	{
		this( project , platform , ( String ) null )
	}

	public Variant( Project project , Platforms platform , String flavor )
	{
		this( project , platform , flavor , null )
	}

	public Variant( Project project , Platforms platform , String flavor , String type )
	{
		this( project , platform , flavor ? [ flavor ] : null , type )
	}

	public Variant( Project project , Platforms platform , List<String> flavors )
	{
		this( project , platform , flavors , null )
	}

	public Variant( Project project , Platforms platform , List<String> flavors , String type )
	{
		this.project = project
		this.platform = platform
		productFlavors = flavors ?: new ArrayList<String>( )
		buildType = type
	}

	public String getName()
	{
		String name = ""

		if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name
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

				if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name.capitalize( )
				productFlavors.each { flavor -> name += flavor.capitalize( ) }

				if( buildType ) name += buildType.capitalize( )

				if( type == NamingTypes.CAPITALIZE_BUT_FIRST ) name = name.substring( 0 , 1 ).toLowerCase( ) + name.substring( 1 )

				return name

			default:

				if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name + type.c
				productFlavors.each { flavor -> name += flavor + type.c }

				if( buildType ) name += buildType + type.c else name.substring( 0 , name.size( ) - 1 )

				return name
		}
	}

	public Platforms getPlatform()
	{
		return platform
	}

	public setPlatform( Platforms platform )
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
}
