package flair.gradle.dependencies

import flair.gradle.utils.PropertyFile
import flair.gradle.utils.Platform
import org.apache.tools.ant.taskdefs.condition.Os
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Sdk
{
	private String path

	String getPath()
	{
		return path
	}

	void setPath( String path )
	{
		this.path = path
	}

	String getName()
	{
		return path ? path.split( "/" ).last( ) : null
	}

	Sdk( Project project )
	{
		this( project , null )
	}

	Sdk( Project project , Platform platform )
	{
		File file = project.file( "local.properties" )

		if( file.exists( ) )
		{
			switch( platform )
			{
				case Platform.IOS:
					this.path = new PropertyFile( file ).getProp( "ios.sdk.dir" )
					break

				case Platform.ANDROID:
					this.path = new PropertyFile( file ).getProp( "android.sdk.dir" )
					break

				case Platform.DESKTOP:
					this.path = new PropertyFile( file ).getProp( "desktop.sdk.dir" )
					break
			}

			if( !this.path ) this.path = new PropertyFile( file ).getProp( "sdk.dir" )
		}

		this.path = this.path ? this.path.replaceAll( "\\\\" , "/" ) : null
	}

	Boolean isAirSdk()
	{
		return path && new File( "${ path }/air-sdk-description.xml" ).exists( )
	}

	String getVersion()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		File description = new File( "${ path }/air-sdk-description.xml" )

		return new XmlParser( ).parseText( description.text ).version.text( ).substring( 0 , 4 )
	}

	String getFullVersion()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		File description = new File( "${ path }/air-sdk-description.xml" )

		Node node = new XmlParser( ).parseText( description.text )

		return node.version.text( ) + "." + node.build.text( )
	}

	String getFrameworkPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		return path + "/frameworks"
	}

	String getMxmlcPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "mxmlc.bat" : "mxmlc"

		//return path + "/lib/mxmlc-cli.jar"// + executable
		return path + "/bin/" + executable
	}

	String getAdtPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adt.bat" : "adt"

		//return path + "/lib/adt.jar"// + executable
		return path + "/bin/" + executable
	}

	String getAdlPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "adl.exe" : "adl"

		return path + "/bin/" + executable
	}

	String getAsdocPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		String executable = Os.isFamily( Os.FAMILY_WINDOWS ) ? "asdoc.bat" : "asdoc"

		return path + "/bin/" + executable
	}

	String getPng2AtfPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		return path + "/atftools/png2atf"
	}

	String getAdbPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		return path + "/lib/android/bin/adb"
	}

	String getIdbPath()
	{
		if( !isAirSdk( ) ) throw new Exception( "Cannot find AIR SDK home" )

		return path + "/lib/aot/bin/iOSBin/idb"
	}
}
