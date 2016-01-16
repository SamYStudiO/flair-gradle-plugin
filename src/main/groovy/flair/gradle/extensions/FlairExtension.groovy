package flair.gradle.extensions

import flair.gradle.extensions.configuration.IPlatformVariantConfigurationContainerExtension
import flair.gradle.extensions.configuration.IVariantConfigurationContainerExtension
import flair.gradle.extensions.configuration.VariantConfigurationContainerExtension
import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtension extends VariantConfigurationContainerExtension implements IPlatformVariantConfigurationContainerExtension
{
	public static final NAME = "flair"

	public String moduleName

	public String packageName

	public Boolean autoGenerateVariantDirectory

	public FlairExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}

	@Override
	public IVariantConfigurationContainerExtension getPlatformContainer( Platform platform )
	{
		return ( platform ? project.flair[ platform.name.toLowerCase( ) ] : project.flair ) as IVariantConfigurationContainerExtension
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] != null || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case "moduleName": return "app"
				case "packageName": return ""
				case "autoGenerateVariantDirectory": return true

				default: return null
			}
		}
	}
}
