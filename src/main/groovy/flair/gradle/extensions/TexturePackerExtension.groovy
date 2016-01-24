package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class TexturePackerExtension extends AbstractExtension
{
	private Boolean generateAtfTexturesFromAtlases

	public TexturePackerExtension( String name , Project project )
	{
		super( name , project )
	}

	public Boolean getGenerateAtfTexturesFromAtlases()
	{
		return generateAtfTexturesFromAtlases
	}

	public void generateAtfTexturesFromAtlases( Boolean generateAtfTexturesFromAtlases )
	{
		this.generateAtfTexturesFromAtlases = generateAtfTexturesFromAtlases
	}

	@Override
	public Object getProp( String property , boolean returnDefaultIfNull )
	{
		if( this[ property ] || !returnDefaultIfNull ) return this[ property ] else
		{
			switch( property )
			{
				case Properties.GENERATE_ATF_TEXTURES_FROM_ATLASES.name: return false

				default: return null
			}
		}
	}
}
