package flair.gradle.plugins

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class MobilePlugin implements Plugin<Project>
{
	@Override
	void apply( Project project )
	{
		project.apply( plugin: "flair.android" )
		project.apply( plugin: "flair.ios" )
	}
}
