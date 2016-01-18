package flair.gradle.extensions.configuration

import flair.gradle.variants.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ADLExtension extends AbstractConfigurationExtension implements IConfigurationExtension
{
	public String screensize

	public int XscreenDPI

	public ADLExtension( String name , Project project , Platform platform )
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
				case "screensize": return "540x960:540x960"
				case "XscreenDPI": return 240

				default: return null
			}
		}
	}
}
