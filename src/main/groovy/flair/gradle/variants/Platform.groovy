package flair.gradle.variants

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Platform {

	IOS( "Ios" ) ,
	ANDROID( "Android" ) ,
	DESKTOP( "Desktop" )

	private String name

	Platform( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}