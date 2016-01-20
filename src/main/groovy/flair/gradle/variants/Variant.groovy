package flair.gradle.variants

import flair.gradle.plugins.PluginManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public final class Variant
{
	protected Project project

	protected Platform platform

	protected List<String> productFlavors

	protected String buildType

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

		if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name
		productFlavors.each { flavor -> name += flavor }

		if( buildType ) name += buildType

		return name
	}

	public enum NamingType
	{
		CAPITALIZE( null ) ,
		CAPITALIZE_BUT_FIRST( null ) ,
		UNDERSCORE( '_' ) ,
		HYPHEN( '-' ) ,
		SPACE( ' ' )

		private Character c

		public NamingType( Character c )
		{
			this.c = c
		}

		public Character getC()
		{
			return c
		}
	}

	public String getNameWithType( NamingType type )
	{
		String name = ""

		switch( type )
		{
			case NamingType.CAPITALIZE:
			case NamingType.CAPITALIZE_BUT_FIRST:

				if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name.capitalize( )
				productFlavors.each { flavor -> name += flavor.capitalize( ) }

				if( buildType ) name += buildType.capitalize( )

				if( type == NamingType.CAPITALIZE_BUT_FIRST ) name = name.substring( 0 , 1 ).toLowerCase( ) + name.substring( 1 )

				return name

			default:

				if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name + type.c
				productFlavors.each { flavor -> name += flavor + type.c }

				if( buildType ) name += buildType.capitalize( ) else name.substring( 0 , name.size( ) - 1 )

				return name
		}
	}

	public Platform getPlatform()
	{
		return platform
	}

	public List<String> getProductFlavors()
	{
		return productFlavors
	}

	public String getBuildType()
	{
		return buildType
	}
}
