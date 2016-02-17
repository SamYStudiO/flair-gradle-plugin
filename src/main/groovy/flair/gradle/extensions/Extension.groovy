package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Extension {

	FLAIR( "flair" ) ,
	TEXTURE_PACKER( "texturePacker" )

	private String name

	public Extension( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}