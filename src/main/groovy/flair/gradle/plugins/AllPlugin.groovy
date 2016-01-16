package flair.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class AllPlugin implements Plugin<Project>
{
	@Override
	public void apply( Project project )
	{
		project.apply( plugin: "flair.ios" )
		project.apply( plugin: "flair.android" )
		project.apply( plugin: "flair.desktop" )
	}
}
