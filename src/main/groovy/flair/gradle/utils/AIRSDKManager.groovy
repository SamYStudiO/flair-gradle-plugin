package flair.gradle.utils

import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public final class AIRSDKManager
{
	public static String getPath( Project project )
	{
		File local = project.rootProject.file( "local.properties" )

		if( !local.exists( ) ) throw new IllegalArgumentException( "local.properties file is missing, add it to your project root" )

		Properties properties = new Properties( )
		properties.load( local.newDataInputStream( ) )

		String path = properties.getProperty( "sdk.dir" )

		if( path == null || path.isEmpty( ) ) throw new IllegalArgumentException( "sdk.dir property is missing or empty from your local.properties file" )

		if( !new File( path ).exists( ) ) throw new IllegalArgumentException( "sdk.dir path from your local.properties is not a valid path" )

		return properties.getProperty( "sdk.dir" )
	}

	public static Boolean isFlexSDK( Project project )
	{
		return new File( getPath( project ) + "/flex-sdk-description.xml" ).exists( )
	}

	public static String getVersion( Project project )
	{
		File description = new File( getPath( project ) + "/air-sdk-description.xml" )

		if( !description.exists( ) ) throw new IllegalArgumentException( "Invalid SDK (maybe you are using Flex SDK without merged AIR SDK)" )

		return new XmlParser( ).parseText( description.getText( ) ).version.text( ).substring( 0 , 4 )
	}

	public static String getMXMLCPath( Project project )
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "amxmlc.bat" : "amxmlc"

		return getPath( project ) + "/bin/" + executable
	}

	public static String getADTPath( Project project )
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adt.bat" : "adt"

		return getPath( project ) + "/bin/" + executable
	}

	public static String getADLPath( Project project )
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adl.exe" : "adl"

		return getPath( project ) + "/bin/" + executable
	}

	public static String getASDOCPath( Project project )
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "aasdoc.bat" : "aasdoc"

		return getPath( project ) + "/bin/" + executable
	}

	private AIRSDKManager()
	{
	}
}
