package flair.gradle.plugins

import flair.gradle.extensions.FlairExtension
import flair.gradle.structure.ClassTemplateStructure
import flair.gradle.structure.CommonStructure
import flair.gradle.structure.VariantStructure
import flair.gradle.tasks.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
class BasePlugin extends AbstractStructurePlugin
{
	@Override
	protected void addStructures()
	{
		addStructure( new CommonStructure( ) )
		addStructure( new ClassTemplateStructure( ) )
		addStructure( new VariantStructure( ) )
		addStructure( new CommonStructure( ) )
	}

	@Override
	protected void addTasks()
	{
		addTask( Task.CLEAN.name , Task.CLEAN.type )
		//addTask( Task.ASSEMBLE.name , Group.BUILD )
		//addTask( Task.COMPILE.name , Group.BUILD )

		org.gradle.api.plugins.PluginManager
	}

	@Override
	protected void addExtensions()
	{
		addConfigurationExtension( FlairExtension.NAME , null , FlairExtension )

		/*String moduleName = flairExtension.getFlairProperty( "moduleName" )


		flairExtension.watcher.watch( project.file( moduleName ) ) {

			println( "change" )

			Node node = new XmlParser( ).parse( project.file( "${ moduleName }/app.iml" ) )

			project.file( "${ project.buildDir }/${ node.component.@active }" ).createNewFile( )
		}*/
	}
}
