package flair.gradle.extensions.configuration

import flair.gradle.variants.Platform
import flair.gradle.plugins.AndroidPlugin
import flair.gradle.plugins.DesktopPlugin
import flair.gradle.plugins.IOSPlugin
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ConfigurationContainerExtension extends AbstractConfigurationExtension implements IConfigurationContainerExtension
{
	protected AppDescriptorExtension appDescriptor

	protected ADLExtension adl

	public List<String> excludeResources

	public ConfigurationContainerExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		appDescriptor = new AppDescriptorExtension( ConfigurationExtension.APP_DESCRIPTOR.name , project , platform )
		adl = new ADLExtension( ConfigurationExtension.ADL.name , project , platform )
	}

	public void appDescriptor( Action<AppDescriptorExtension> action )
	{
		action.execute( appDescriptor )
	}

	public void adl( Action<ADLExtension> action )
	{
		action.execute( adl )
	}

	@Override
	public IConfigurationExtension getConfiguration( String name )
	{
		return this[ name ] as IConfigurationExtension
	}

	@Override
	public IConfigurationExtension getAdl()
	{
		return adl
	}

	@Override
	public IConfigurationExtension getAppDescriptor()
	{
		return appDescriptor
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] != null || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case "excludeResources":

					Platform p = platform

					if( p == null ) p = detectDefaultPlatform( )
					if( p == null ) return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]

					switch( p )
					{
						case p.IOS: return [ "drawable*-ldpi*/**" , "drawable*-mdpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxxhdpi*/**" ]
						case p.ANDROID: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
						case p.DESKTOP: return [ "drawable*-ldpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxhdpi*/**" , "drawable*-xxxhdpi*/**" ]

						default: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
					}

				default: return null
			}
		}
	}

	private Platform detectDefaultPlatform()
	{
		List<Platform> list = new ArrayList<Platform>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IOSPlugin && list.indexOf( Platform.IOS ) < 0 ) list.add( Platform.IOS )
			if( plugin instanceof AndroidPlugin && list.indexOf( Platform.ANDROID ) < 0 ) list.add( Platform.ANDROID )
			if( plugin instanceof DesktopPlugin && list.indexOf( Platform.DESKTOP ) < 0 ) list.add( Platform.DESKTOP )
		}

		if( list.size( ) == 1 ) return list.first( )

		return null
	}
}
