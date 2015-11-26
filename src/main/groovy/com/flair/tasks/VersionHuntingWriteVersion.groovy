package com.flair.tasks

import groovyx.net.http.HTTPBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static groovyx.net.http.ContentType.URLENC

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class VersionHuntingWriteVersion extends DefaultTask
{
	public VersionHuntingWriteVersion()
	{
		group = "version hunting"
		description = ""
	}

	@TaskAction
	public void writeVersion()
	{
		String url = project.flair.versionHuntingURL
		String id = project.flair.versionHuntingID
		String moduleName = project.flair.moduleName

		if( url.isEmpty( ) || id.isEmpty( ) ) throw new IllegalArgumentException( "Missing versionHuntingURL or versionHuntingID property add\nflair {\n	versionHuntingURL = \"url\"\n	versionHuntingID = \"id\"\n}\nto your build.gradle file." )

		HTTPBuilder http = new HTTPBuilder( url )
		def body = [ id_swf: id , inc: "false" ]
		String result = http.post( path: url , body: body , requestContentType: URLENC )

		String major = "0";
		String minor = "0";
		String build = "0";

		result.eachLine {
			if( it.indexOf( "major=" ) >= 0 ) major = it.replace( "major=" , "" )
			if( it.indexOf( "minor=" ) >= 0 ) minor = it.replace( "minor=" , "" )
			if( it.indexOf( "build=" ) >= 0 ) build = it.replace( "build=" , "" )
		}
		
		writeApp( project.file( "${ moduleName }/src/main/resources/android/app_descriptor.xml" ) , "${ major }.${ minor }.${ build }" )
		writeApp( project.file( "${ moduleName }/src/main/resources/ios/app_descriptor.xml" ) , "${ major }.${ minor }.${ build }" )
		writeApp( project.file( "${ moduleName }/src/main/resources/desktop/app_descriptor.xml" ) , "${ major }.${ minor }.${ build }" )
	}

	private void writeApp( File app , String version )
	{
		String content = app.getText( )

		content = content.replaceAll( "<versionNumber>[0-9]*\\.[0-9]*\\.[0-9]*<\\/versionNumber>" , "<versionNumber>${ version }</versionNumber>" )

		app.write( content )
	}
}
