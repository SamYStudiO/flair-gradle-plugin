package com.flair.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * @author Fabrique on 24/11/2015.
 */
public class VersionHunting extends DefaultTask
{
	public VersionHunting()
	{
		group = "version hunting"
		description = ""
	}

	@TaskAction
	public void getVersion()
	{
	}
}
