package flair.gradle.utils

import org.apache.tools.ant.taskdefs.condition.Os

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public enum Platform {

	IOS( "ios" , "ipa" ) ,
	ANDROID( "android" , "apk" ) ,
	DESKTOP( "desktop" , Os.isFamily( Os.FAMILY_MAC ) ? "dmg" : "exe" )

	private String name

	private String extension

	public Platform( String name , String extension )
	{
		this.name = name
		this.extension = extension
	}

	public String getName()
	{
		return name
	}

	public String getExtension()
	{
		return extension
	}
}