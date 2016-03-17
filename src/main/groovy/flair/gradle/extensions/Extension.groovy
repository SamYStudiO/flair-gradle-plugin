package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Extension {

	FLAIR( "flair" )

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