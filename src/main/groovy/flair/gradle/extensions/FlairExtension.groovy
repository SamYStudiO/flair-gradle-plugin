package flair.gradle.extensions

import flair.gradle.extensions.configuration.IPlatformVariantConfigurationContainerExtension
import flair.gradle.extensions.configuration.IVariantsConfigurationContainerExtension
import flair.gradle.extensions.configuration.VariantsConfigurationContainerExtension
import flair.gradle.platforms.Platform
import flair.gradle.plugins.PluginManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtension extends VariantsConfigurationContainerExtension implements IPlatformVariantConfigurationContainerExtension
{
	public static final NAME = "flair"

	public String moduleName

	public String packageName

	public Boolean autoGenerateVariantDirectories

	public FlairExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}

	@Override
	public IVariantsConfigurationContainerExtension getPlatformContainer( Platform platform )
	{
		return ( platform && PluginManager.hasPlatformPlugin( project , platform ) ? project.flair[ platform.name.toLowerCase( ) ] : project.flair ) as IVariantsConfigurationContainerExtension
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		Object value = super.getProp( property , returnDefaultIfNull )

		if( value != null || !returnDefaultIfNull ) return value else
		{
			switch( property )
			{
				case "moduleName": return "app"
				case "packageName": return ""
				case "autoGenerateVariantDirectories": return true

				default: return null
			}
		}
	}
}
