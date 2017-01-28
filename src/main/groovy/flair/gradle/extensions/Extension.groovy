package flair.gradle.extensions

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
enum Extension {

	FLAIR( "flair" )

	private String name

	Extension( String name )
	{
		this.name = name
	}

	String getName()
	{
		return name
	}
}