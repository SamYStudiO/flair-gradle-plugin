package flair.gradle.plugins

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
abstract class AbstractPlugin implements IPlugin
{
	private Project project

	Project getProject()
	{
		return project
	}

	@Override
	void apply( Project project )
	{
		this.project = project

		if( !( this instanceof BasePlugin ) ) project.apply( plugin: BasePlugin )

		addTasks( )
	}

	protected void addTasks()
	{
	}
}
