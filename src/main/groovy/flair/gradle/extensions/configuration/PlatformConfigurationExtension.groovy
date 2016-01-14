package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PlatformConfigurationExtension extends ConfigurationExtension
{
	public String mainClass = ""

	public PlatformConfigurationExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		switch( platform )
		{
			case Platform.IOS: mainClass = "MainIOS"
				break
			case Platform.ANDROID: mainClass = "MainAndroid"
				break
			case Platform.DESKTOP: mainClass = "MainDesktop"
				break
		}
	}
}
