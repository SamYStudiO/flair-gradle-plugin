package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AppDescriptorExtension extends AbstractConfigurationExtension implements IConfigurationExtension
{
	public String id

	public String idSuffix

	public String appName

	public String appNameSuffix

	public String version

	public Boolean fullScreen

	public String aspectRatio

	public Boolean autoOrient

	public Boolean depthAndStencil

	public String defaultSupportedLanguages

	public AppDescriptorExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] != null || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case "id": return PropertyManager.getProperty( project , "packageName" )
				case "idSuffix": return ""
				case "appName": return project.name
				case "appNameSuffix": return ""
				case "version": return "0.0.0"
				case "fullScreen": return true
				case "aspectRatio": return "any"
				case "autoOrient": return true
				case "depthAndStencil": return false
				case "defaultSupportedLanguages": return "en"

				default: return null
			}
		}
	}
}
