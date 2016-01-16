package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class PropertyManager
{
	public static IPlatformVariantConfigurationContainerExtension getRootContainer( Project project )
	{
		return project.flair as IPlatformVariantConfigurationContainerExtension
	}

	public static String getProperty( Project project , String name )
	{
		getProperty( project , name , null , "" , "" )
	}

	public static String getProperty( Project project , String name , Platform platform )
	{
		getProperty( project , name , platform , "" , "" )
	}

	public static String getProperty( Project project , String configurationName , String name , Platform platform )
	{
		getProperty( project , configurationName , name , platform , "" , "" )
	}

	public static String getProperty( Project project , String name , Platform platform , String productFlavor , String buildType )
	{
		Object value

		value = getRootContainer( project ).getPlatformContainer( platform ).getBuildType( buildType ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getBuildType( buildType ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getPlatformContainer( platform ).getProductFlavor( productFlavor ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getProductFlavor( productFlavor ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getPlatformContainer( platform ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getProp( name )
		if( value ) return value

		return getRootContainer( project ).getPlatformContainer( platform ).getProp( name , true )
	}

	public
	static String getProperty( Project project , String configurationName , String name , Platform platform , String productFlavor , String buildType )
	{
		Object value

		value = getRootContainer( project ).getPlatformContainer( platform ).getBuildType( buildType ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getBuildType( buildType ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getPlatformContainer( platform ).getProductFlavor( productFlavor ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getProductFlavor( productFlavor ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getPlatformContainer( platform ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		return getRootContainer( project ).getPlatformContainer( platform ).getConfiguration( configurationName ).getProp( name , true )
	}
}
