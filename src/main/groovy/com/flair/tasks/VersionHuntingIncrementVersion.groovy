package com.flair.tasks

import groovyx.net.http.HTTPBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

import static groovyx.net.http.ContentType.URLENC

/**
 * @author SamYStudiO on 24/11/2015.
 */
public class VersionHuntingIncrementVersion extends DefaultTask
{
	public VersionHuntingIncrementVersion()
	{
		group = "version hunting"
		description = ""
	}

	@TaskAction
	public void incrementVersion()
	{
		String url = project.flair.versionHuntingURL;
		String id = project.flair.versionHuntingID;

		HTTPBuilder http = new HTTPBuilder( url )
		def body = [ id_swf: id , inc: "true" ]
		http.post( path: url , body: body , requestContentType: URLENC )
	}
}
