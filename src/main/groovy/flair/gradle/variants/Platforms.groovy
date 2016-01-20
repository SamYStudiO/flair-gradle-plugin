package flair.gradle.variants

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Platforms {

	IOS( "Ios" ) ,
	ANDROID( "Android" ) ,
	DESKTOP( "Desktop" )

	private String name

	Platforms( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}