package flair.gradle.extensions

import flair.gradle.plugins.AndroidPlugin
import flair.gradle.plugins.DesktopPlugin
import flair.gradle.plugins.IosPlugin
import flair.gradle.variants.Platforms
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformExtension extends AbstractExtension implements IPlatformExtension
{
	private Platforms platform

	public Platforms getPlatform()
	{
		return platform
	}

	private AppDescriptorExtension appDescriptor

	private AdlExtension adl

	public List<String> excludeResources

	public AbstractPlatformExtension( String name , Project project , Platforms platform )
	{
		super( name , project )

		this.platform = platform

		appDescriptor = new AppDescriptorExtension( Extensions.APP_DESCRIPTOR.name , project )
		adl = new AdlExtension( Extensions.ADL.name , project )
	}

	public void appDescriptor( Action<AppDescriptorExtension> action )
	{
		action.execute( appDescriptor )
	}

	public void adl( Action<AdlExtension> action )
	{
		action.execute( adl )
	}

	@Override
	public IExtension getExtension( String name )
	{
		return this[ name ] as IExtension
	}

	@Override
	public IExtension getAdl()
	{
		return adl
	}

	@Override
	public IExtension getAppDescriptor()
	{
		return appDescriptor
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case Properties.EXCLUDE_RESOURCES.name:

					Platforms p = platform

					if( p == null ) p = detectDefaultPlatform( )

					switch( p )
					{
						case Platforms.IOS: return [ "drawable*-ldpi*/**" , "drawable*-mdpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platforms.ANDROID: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
						case Platforms.DESKTOP: return [ "drawable*-ldpi*/**" , "drawable*-hdpi*/**" , "drawable*-xxhdpi*/**" , "drawable*-xxxhdpi*/**" ]

						default: return [ "drawable*-ldpi*/**" , "drawable*-xxxhdpi*/**" ]
					}

				default: return null
			}
		}
	}

	private Platforms detectDefaultPlatform()
	{
		List<Platforms> list = new ArrayList<Platforms>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IosPlugin && list.indexOf( Platforms.IOS ) < 0 ) list.add( Platforms.IOS )
			if( plugin instanceof AndroidPlugin && list.indexOf( Platforms.ANDROID ) < 0 ) list.add( Platforms.ANDROID )
			if( plugin instanceof DesktopPlugin && list.indexOf( Platforms.DESKTOP ) < 0 ) list.add( Platforms.DESKTOP )
		}

		if( list.size( ) == 1 ) return list.first( )

		return null
	}
}
