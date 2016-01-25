package flair.gradle.variants

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Platforms {

	IOS( "ios" ) ,
	ANDROID( "android" ) ,
	DESKTOP( "desktop" )

	private String name

	public Platforms( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}