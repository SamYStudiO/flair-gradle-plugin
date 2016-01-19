package flair.gradle.dependencies

import flair.gradle.utils.LocalProperties
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Sdk
{
	private Project project

	private String path

	public String getPath()
	{
		if( path ) return path

		path = new LocalProperties( project.file( "local.properties" ) ).getProp( "sdk.dir" )

		if( path ) return path

		path = System.getenv( "FLEX_HOME" )

		return path
	}

	public void setPath( String path )
	{
		this.path = path
	}

	public Sdk()
	{
	}

	public Sdk( String path )
	{
		this.path = path
	}

	public Sdk( Project project )
	{
		this.project = project
	}

	public Boolean isValid()
	{
		return isAirSdk( ) || isFlexSdk( )
	}

	public Boolean isAirSdk()
	{
		return new File( "${ getPath( ) }/air-sdk-description.xml" ).exists( )
	}

	public Boolean isFlexSdk()
	{
		return new File( "${ getPath( ) }/flex-sdk-description.xml" ).exists( )
	}

	public String getVersion()
	{
		File description = new File( "${ getPath( ) }/air-sdk-description.xml" )

		return new XmlParser( ).parseText( description.getText( ) ).version.text( ).substring( 0 , 4 )
	}

	public String getMxmlcPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "amxmlc.bat" : "amxmlc"

		return getPath( ) + "/bin/" + executable
	}

	public String getAdtPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adt.bat" : "adt"

		return getPath( ) + "/bin/" + executable
	}

	public String getAdlPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adl.exe" : "adl"

		return getPath( ) + "/bin/" + executable
	}

	public String getAsdocPath()
	{
		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "aasdoc.bat" : "aasdoc"

		return getPath( ) + "/bin/" + executable
	}
}
