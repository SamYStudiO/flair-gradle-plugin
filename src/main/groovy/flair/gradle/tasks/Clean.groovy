package flair.gradle.tasks

import org.gradle.api.tasks.TaskAction

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class Clean extends AbstractTask
{
	Clean()
	{
		group = TaskGroup.BUILD.name
		description = "Deletes ${ project.buildDir.name } directory"
	}

	@SuppressWarnings( "GroovyUnusedDeclaration" )
	@TaskAction
	void clean()
	{
		project.buildDir.deleteDir( )
	}
}
