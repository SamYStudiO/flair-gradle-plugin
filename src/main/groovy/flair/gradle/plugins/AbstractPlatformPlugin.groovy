package flair.gradle.plugins

import flair.gradle.structure.StructureManager
import flair.gradle.tasks.Group
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
			TaskManager.updateTasks( project )
			StructureManager.updateVariantDirectories( project )
		}
	}

	@Override
	public void addTasks()
	{
		addTask( Task.SCAFFOLD.name , Task.SCAFFOLD.type )
		addTask( Task.ASSEMBLE.name , Task.ASSEMBLE.type , Group.BUILD )
		addTask( Task.COMPILE.name , Task.COMPILE.type , Group.BUILD )
		addTask( Task.CLEAN.name , Task.CLEAN.type )
		addTask( Task.INCREMENT_VERSION.name , Task.INCREMENT_VERSION.type )
		addTask( Task.GENERATE_FONT_CLASS.name , Task.GENERATE_FONT_CLASS.type )
		addTask( Task.AUTO_GENERATE_FONT_CLASS.name , Task.AUTO_GENERATE_FONT_CLASS.type )
		addTask( Task.GENERATE_RESOURCE_CLASS.name , Task.GENERATE_RESOURCE_CLASS.type )
		addTask( Task.AUTO_GENERATE_RESOURCE_CLASS.name , Task.AUTO_GENERATE_RESOURCE_CLASS.type )
	}
}
