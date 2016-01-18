package flair.gradle.extensions.configuration

import flair.gradle.variants.Platform
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

	public static Object getProperty( Project project , String name )
	{
		getProperty( project , name , null , "" , "" )
	}

	public static Object getProperty( Project project , String name , Platform platform )
	{
		getProperty( project , name , platform , "" , "" )
	}

	public static Object getProperty( Project project , String configurationName , String name , Platform platform )
	{
		getProperty( project , configurationName , name , platform , "" , "" )
	}

	public static Object getProperty( Project project , String name , Platform platform , String productFlavor , String buildType )
	{
		Object value

		if( platform && buildType ) value = getRootContainer( project ).getPlatformContainer( platform ).getBuildType( buildType ).getProp( name )
		if( value ) return value

		if( buildType ) value = getRootContainer( project ).getBuildType( buildType ).getProp( name )
		if( value ) return value

		if( platform && productFlavor ) value = getRootContainer( project ).getPlatformContainer( platform ).getProductFlavor( productFlavor ).getProp( name )
		if( value ) return value

		if( productFlavor ) value = getRootContainer( project ).getProductFlavor( productFlavor ).getProp( name )
		if( value ) return value

		if( platform ) value = getRootContainer( project ).getPlatformContainer( platform ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getProp( name )
		if( value ) return value

		return getRootContainer( project ).getPlatformContainer( platform ).getProp( name , true )
	}

	public
	static Object getProperty( Project project , String configurationName , String name , Platform platform , String productFlavor , String buildType )
	{
		Object value

		if( platform && buildType ) value = getRootContainer( project ).getPlatformContainer( platform ).getBuildType( buildType ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		if( buildType ) value = getRootContainer( project ).getBuildType( buildType ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		if( platform && productFlavor ) value = getRootContainer( project ).getPlatformContainer( platform ).getProductFlavor( productFlavor ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		if( productFlavor ) value = getRootContainer( project ).getProductFlavor( productFlavor ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		if( platform ) value = getRootContainer( project ).getPlatformContainer( platform ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		value = getRootContainer( project ).getConfiguration( configurationName ).getProp( name )
		if( value ) return value

		return getRootContainer( project ).getPlatformContainer( platform ).getConfiguration( configurationName ).getProp( name , true )
	}
}
