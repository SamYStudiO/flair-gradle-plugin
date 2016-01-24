package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Extensions {

	FLAIR( "flair" ) ,
	TEXTURE_PACKER( "texturePacker" )

	private String name

	Extensions( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}