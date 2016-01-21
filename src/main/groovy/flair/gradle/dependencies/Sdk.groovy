package flair.gradle.dependencies

import flair.gradle.plugins.IdePlugin
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

		if( project )
		{
			boolean found

			project.plugins.each {

				if( it instanceof IdePlugin )
				{
					if( !found && it.sdk.isValid( ) )
					{
						found = true
						path = it.sdk.path
						name = it.sdk.name
					}
				}
			}

			if( found ) return path
		}

		try
		{
			path = new LocalProperties( project.file( "local.properties" ) ).getProp( "sdk.dir" )
		}
		catch( NullPointerException e )
		{}

		if( path ) return path

		path = System.getenv( "AIR_HOME" )

		if( path ) return path

		path = System.getenv( "AIR_SDK_HOME" )

		if( path ) return path

		path = System.getenv( "FLEX_HOME" )

		if( path ) return path

		path = System.getenv( "FLEX_SDK_HOME" )

		if( path ) return path

		throw new Exception( "Cannot find AIR SDK home, try setting sdk.dir property from local.properties file in your project root or set AIR_HOME/FLEX_HOME environment variable" )
	}

	public void setPath( String path )
	{
		this.path = path
	}

	private String name

	public String getName()
	{
		if( name ) return name

		String path = getPath( )

		if( name ) return name

		if( path )
		{
			if( path.indexOf( "/" ) > 0 )
			{
				String[] a = path.split( "/" )

				return a[ a.size( ) - 1 ]
			}
			else return path
		}

		return ""
	}

	public void setName( String name )
	{
		this.name = name
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

	public Sdk( Project project )
	{
		this.project = project
	}

	public Boolean isValid()
	{
		return path && isAirSdk( )
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
