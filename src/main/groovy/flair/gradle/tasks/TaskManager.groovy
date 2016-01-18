package flair.gradle.tasks

import flair.gradle.platforms.Platform
import flair.gradle.plugins.PluginManager
import flair.gradle.tasks.factory.*
import flair.gradle.variants.Variant
import flair.gradle.variants.VariantManager
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.TaskContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class TaskManager
{
	public
	static Task getVariantTask( Project project , Group group , Variant variant )
	{
		return project.tasks.getByName( group.name + variant.name )
	}

	public
	static TaskContainer getVariantTasks( Project project )
	{
		return project.tasks.findAll { task -> task instanceof IVariantTask }
	}

	public
	static List<String> getVariantTaskNames( Project project , Group group )
	{
		List<String> list = new ArrayList<String>( )

		TaskContainer c = project.tasks.findAll { task -> task.group == group.name }

		c.each { list.add( it.name ) }

		return list
	}

	public
	static void removeVariantTasks( Project project )
	{
		Iterator<Task> iterator = getVariantTasks( project ).iterator( )

		while( iterator.hasNext( ) )
		{
			project.tasks.remove( iterator.next( ) )
		}
	}

	public
	static void updateTasks( Project project )
	{
		removeVariantTasks( project )

		updateVariantTasks( project , new ProcessResourceTaskFactory( ) )
		updateVariantTasks( project , new BuildTaskFactory( ) )
		updateVariantTasks( project , new PackageTaskFactory( ) )
		updateVariantTasks( project , new InstallTaskFactory( ) )
		updateVariantTasks( project , new LaunchTaskFactory( ) )

		project.tasks.getByName( "build" ).dependsOn( getVariantTaskNames( project , Group.BUILD ) )
	}

	public
	static void updateVariantTasks( Project project , VariantTaskFactory factory )
	{
		List<Platform> platforms = PluginManager.getCurrentPlatforms( project )

		platforms.each { platform ->
			VariantManager.getVariants( project , platform ).each { variant -> factory.create( project , variant ) as Task
			}
		}
	}
}
