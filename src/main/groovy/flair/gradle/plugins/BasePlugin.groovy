package flair.gradle.plugins

import flair.gradle.extensions.FlairExtension
import flair.gradle.ide.Ide
import flair.gradle.ide.Idea
import flair.gradle.structure.ClassTemplateStructure
import flair.gradle.structure.CommonStructure
import flair.gradle.structure.VariantStructure
import flair.gradle.tasks.IVariantTask
import flair.gradle.tasks.Tasks
import flair.gradle.watcher.executables.GenerateFontClass
import flair.gradle.watcher.executables.GenerateRClass
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class BasePlugin extends AbstractStructurePlugin
{
	private List<Ide> ides = new ArrayList<Ide>( )

	@Override
	public void apply( Project project )
	{
		ides.add( new Idea( project ) )

		super.apply( project )

		project.configurations.create(  )
		project.dependencies.create(  )

		project.beforeEvaluate {
			removeVariantTasks( )
		}

		project.afterEvaluate {
			new GenerateRClass( ).execute( project )
			new GenerateFontClass( ).execute( project )

			ides.each {
				if( it.isActive ) it.refresh( )
			}
		}
	}

	@Override
	protected void addStructures()
	{
		addStructure( new CommonStructure( ) )
		addStructure( new ClassTemplateStructure( ) )
		addStructure( new VariantStructure( ) )

		ides.each {
			if( it.isActive ) addStructure( it.structure )
		}
	}

	@Override
	protected void addTasks()
	{
		addTask( Tasks.CLEAN.name , Tasks.CLEAN.type )
		//addTask( Tasks.ASSEMBLE.name , Groups.BUILD )
		//addTask( Tasks.COMPILE.name , Groups.BUILD )
	}

	@Override
	protected void addExtensions()
	{
		addConfigurationExtension( FlairExtension.NAME , null , FlairExtension )
	}

	@Override
	protected void ready()
	{
		flairExtension.watcher.watchPattern( File.separator + "resources" , new GenerateRClass( ) )
		flairExtension.watcher.watchPattern( File.separator + "fonts" , new GenerateFontClass( ) )
	}

	private void removeVariantTasks()
	{
		Iterator<org.gradle.api.Task> iterator = project.tasks.findAll { it instanceof IVariantTask }.iterator( )

		while( iterator.hasNext( ) )
		{
			project.tasks.remove( iterator.next( ) )
		}
	}
}
