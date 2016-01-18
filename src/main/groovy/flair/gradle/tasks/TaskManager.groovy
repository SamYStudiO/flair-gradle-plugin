package flair.gradle.tasks

import flair.gradle.platforms.Platform
import flair.gradle.plugins.PluginManager
import flair.gradle.tasks.factory.*
import flair.gradle.variants.Variant
import flair.gradle.variants.VariantManager
import org.gradle.api.Project
import org.gradle.api.Task

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
	static Set<Task> getVariantTasks( Project project )
	{
		return project.tasks.findAll { it instanceof IVariantTask }
	}

	public
	static List<String> getVariantTaskNames( Project project , Group group )
	{
		List<String> list = new ArrayList<String>( )

		Set<Task> c = project.tasks.findAll { task -> task.group == group.name }

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

		updateVariantTasks( project , new AssembleTaskFactory( ) )
		updateVariantTasks( project , new CompileTaskFactory( ) )
		updateVariantTasks( project , new PackageTaskFactory( ) )
		updateVariantTasks( project , new InstallTaskFactory( ) )
		updateVariantTasks( project , new LaunchADLTaskFactory( ) )

		project.tasks.getByName( flair.gradle.tasks.Task.ASSEMBLE.name ).dependsOn( getVariantTaskNames( project , Group.ASSEMBLE ) )
		project.tasks.getByName( flair.gradle.tasks.Task.COMPILE.name ).dependsOn( getVariantTaskNames( project , Group.COMPILE ) )
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
