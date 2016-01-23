package flair.gradle.dependencies

import org.apache.tools.ant.taskdefs.condition.Os

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class Sdk
{
	private String path

	public String getPath()
	{
		return path
	}

	public void setPath( String path )
	{
		this.path = path
	}

	private String name

	public String getName()
	{
		return name ?: path ? path.split( "/" ).last( ) : null
	}

	public Sdk( String path )
	{
		this.path = path
	}

	public Sdk( String path , String name )
	{
		this.path = path
		this.name = name
	}

	public Boolean isAirSdk()
	{
		return path && new File( "${ path }/air-sdk-description.xml" ).exists( )
	}

	public String getVersion()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		File description = new File( "${ path }/air-sdk-description.xml" )

		return new XmlParser( ).parseText( description.getText( ) ).version.text( ).substring( 0 , 4 )
	}

	public String getMxmlcPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "mxmlc.bat" : "mxmlc"

		return path + "/bin/" + executable
	}

	public String getAdtPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adt.bat" : "adt"

		return path + "/bin/" + executable
	}

	public String getAdlPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adl.exe" : "adl"

		return path + "/bin/" + executable
	}

	public String getAsdocPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "asdoc.bat" : "asdoc"

		return path + "/bin/" + executable
	}
}
