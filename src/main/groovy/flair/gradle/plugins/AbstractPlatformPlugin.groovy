package flair.gradle.plugins

import flair.gradle.tasks.Task
import flair.gradle.tasks.TaskManager

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformPlugin extends AbstractPlugin
{
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

		//project.afterEvaluate {
			//TaskManager.updateVariantTasks( project )
		//}
	}
}
