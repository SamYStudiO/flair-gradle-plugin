package flair.gradle.utils

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Platform {

	IOS( "ios" ) ,
	ANDROID( "android" ) ,
	DESKTOP( "desktop" )

	private String name

	public Platform( String name )
	{
		this.name = name
	}

	public String getName()
	{
		return name
	}
}