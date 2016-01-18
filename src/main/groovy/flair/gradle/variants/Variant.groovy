package flair.gradle.variants

import flair.gradle.platforms.Platform
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

		if( platform && !PluginManager.hasSinglePlatform( project ) ) name += platform.name.capitalize( )
		productFlavors.each { flavor -> name += flavor.capitalize( ) }

		if( buildType ) name += buildType.capitalize( )

		return name
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
