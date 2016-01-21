package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AdlExtension extends AbstractExtension
{
	public String screensize

	public int XscreenDPI

	public AdlExtension( String name , Project project )
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
				case Properties.ADL_SCREEN_SIZE.name: return "540x960:540x960"
				case Properties.ADL_X_SCREEN_DPI.name: return 240

				default: return null
			}
		}
	}
}
