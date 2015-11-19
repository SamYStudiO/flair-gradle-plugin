package com.flair

import com.flair.tasks.Scaffold
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO on 08/11/2015.
 */
class Flair implements Plugin<Project>
{
	@Override
	public void apply( Project project )
	{
		project.getExtensions().create( "flair", FlairProperties )
		project.getTasks().create( "scaffold", Scaffold )
	}
}
