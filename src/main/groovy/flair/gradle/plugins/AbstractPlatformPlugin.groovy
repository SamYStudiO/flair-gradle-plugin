package flair.gradle.plugins

import flair.gradle.tasks.Task
import flair.gradle.tasks.TaskManager
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformPlugin extends AbstractPlugin
{
	@Override
	public void apply( Project project )
	{
		super.apply( project )

		project.afterEvaluate {

			TaskManager.updateProcessResourceTasks( project )

			project.plugins.whenObjectAdded {
				TaskManager.updateProcessResourceTasks( project )
			}
		}
	}

	@Override
	public void addTasks()
	{
		addTask( Task.SCAFFOLD.name , Task.SCAFFOLD.type )
		addTask( Task.UPDATE_PROPERTIES.name , Task.UPDATE_PROPERTIES.type )
		addTask( Task.INCREMENT_VERSION.name , Task.INCREMENT_VERSION.type )
		addTask( Task.GENERATE_FONT_CLASS.name , Task.GENERATE_FONT_CLASS.type )
		addTask( Task.AUTO_GENERATE_FONT_CLASS.name , Task.AUTO_GENERATE_FONT_CLASS.type )
		addTask( Task.GENERATE_RESOURCE_CLASS.name , Task.GENERATE_RESOURCE_CLASS.type )
		addTask( Task.AUTO_GENERATE_RESOURCE_CLASS.name , Task.AUTO_GENERATE_RESOURCE_CLASS.type )
	}
}
