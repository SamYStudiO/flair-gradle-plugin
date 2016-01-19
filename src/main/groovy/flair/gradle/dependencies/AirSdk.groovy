package flair.gradle.dependencies

import flair.gradle.utils.LocalProperties
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AirSdk
{
	private Project project

	private String path

	public String getPath()
	{
		if( path ) return path

		path = new LocalProperties( project.file( "local.properties" ) ).getProp( "sdk.dir" )

		return path
	}

	public void setPath( String path )
	{
		this.path = path
	}

	public AirSdk( String path )
	{
		this.path = path
	}

	public AirSdk( Project project )
	{
		this.project = project
	}

	public Boolean isValidSdk()
	{
		return isAirSdk( ) || isFlexSdk( )
	}

	public Boolean isAirSdk()
	{
		return new File( "${ path }/air-sdk-description.xml" ).exists( )
	}

	public Boolean isFlexSdk()
	{
		return new File( "${ path }/flex-sdk-description.xml" ).exists( )
	}

	public String getVersion()
	{
		File description = new File( "${ path }/air-sdk-description.xml" )

		return new XmlParser( ).parseText( description.getText( ) ).version.text( ).substring( 0 , 4 )
	}

	public String getMxmlcPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "amxmlc.bat" : "amxmlc"

		return path + "/bin/" + executable
	}

	public String getAdtPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adt.bat" : "adt"

		return path + "/bin/" + executable
	}

	public String getAdlPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adl.exe" : "adl"

		return path + "/bin/" + executable
	}

	public String getAsdocPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "aasdoc.bat" : "aasdoc"

		return path + "/bin/" + executable
	}
}
