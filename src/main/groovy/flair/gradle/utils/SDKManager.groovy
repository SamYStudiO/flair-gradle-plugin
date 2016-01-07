package flair.gradle.utils

import org.gradle.api.Project

import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * @author SamYStudiO on 28/11/2015.
 */
public final class SDKManager
{
	public static String getVersion( Project project )
	{
		String path = getPath( project )
		File sdk = new File( path + File.separator + "airsdk.xml" )
		
		Pattern p = Pattern.compile( "http://ns.adobe.com/air/sdk/[0-9]+\\.[0-9]" );
		Matcher m = p.matcher( sdk.getText( ) );

		if( m.find( ) )
		{
			return m.group( 0 ).replace( "http://ns.adobe.com/air/sdk/" , "" )
		}

		return ""
	}

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

	private SDKManager()
	{
	}
}
