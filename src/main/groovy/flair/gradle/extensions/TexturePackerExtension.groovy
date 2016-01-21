package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class TexturePackerExtension extends AbstractExtension
{
	public Boolean generateAtfTexturesFromAtlases

	public TexturePackerExtension( String name , Project project )
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
				case Properties.GENERATE_ATF_TEXTURES_FROM_ATLASES.name: return true

				default: return null
			}
		}
	}
}
