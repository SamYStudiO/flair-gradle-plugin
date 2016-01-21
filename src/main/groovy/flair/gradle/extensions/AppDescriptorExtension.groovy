package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class AppDescriptorExtension extends AbstractExtension
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

	public AppDescriptorExtension( String name , Project project )
	{
		super( name , project )
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case Properties.APP_ID.name: return extensionManager.getFlairProperty( "packageName" )
				case Properties.APP_ID_SUFFIX.name: return ""
				case Properties.APP_NAME.name: return project.name
				case Properties.APP_NAME_SUFFIX.name: return ""
				case Properties.APP_VERSION.name: return "1.0.0"
				case Properties.APP_FULL_SCREEN.name: return true
				case Properties.APP_ASPECT_RATIO.name: return "any"
				case Properties.APP_AUTO_ORIENT.name: return true
				case Properties.APP_DEPTH_AND_STENCIL.name: return false
				case Properties.APP_DEFAULT_SUPPORTED_LANGUAGES.name: return "en"

				default: return null
			}
		}
	}
}
