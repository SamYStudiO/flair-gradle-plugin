package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ConfigurationExtension extends AbstractExtension
{
	protected AppDescriptorExtension appDescriptor = new AppDescriptorExtension( )

	protected ADLExtension adl = new ADLExtension( )

	protected Platform platform

	public String excludeResources = "drawable*-ldpi*/**,drawable*-xxxhdpi*/**"

	public ConfigurationExtension( String name , Project project , Platform platform )
	{
		super( name , project )

		this.platform = platform

		switch( platform )
		{
			case Platform.IOS: excludeResources = "drawable*-mdpi*/**,drawable*-hdpi*/**"
				break
			case Platform.ANDROID: excludeResources = ""
				break
			case Platform.DESKTOP: excludeResources = "drawable*-hdpi*/**,drawable*-xxhdpi*/**"
				break
		}
	}

	public void appDescriptor( Action<AppDescriptorExtension> action )
	{
		action.execute( appDescriptor )
	}

	public void adl( Action<ADLExtension> action )
	{
		action.execute( adl )
	}
}
