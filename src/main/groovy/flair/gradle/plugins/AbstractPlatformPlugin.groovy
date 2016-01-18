package flair.gradle.plugins

import flair.gradle.extensions.configuration.VariantsConfigurationContainerExtension
import flair.gradle.structure.ClassTemplateStructure
import flair.gradle.structure.CommonStructure
import flair.gradle.structure.VariantStructure
import flair.gradle.tasks.Group
import flair.gradle.tasks.IVariantTask
import flair.gradle.tasks.Task
import flair.gradle.tasks.variantFactories.*
import flair.gradle.variants.Platform
import flair.gradle.variants.VariantManager
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformPlugin extends AbstractStructurePlugin implements IPlatformPlugin
{
	protected List<IVariantTaskFactory> variantFactories = new ArrayList<IVariantTaskFactory>( )

	protected Platform platform

	@Override
	public Platform getPlatform()
	{
		return platform
	}

	@Override
	public void setPlatform( Platform platform )
	{
		this.platform = platform
	}

	@Override
	public void apply( Project project )
	{
		super.apply( project )

		addVariantFactories( )

		project.afterEvaluate {
			updateVariantTasks( )
		}
	}

	@Override
	public void addTasks()
	{
		addTask( Task.ASSEMBLE.name , Group.BUILD )
		addTask( Task.COMPILE.name , Group.BUILD )
		addTask( Task.CLEAN.name , Task.CLEAN.type )
		//addTask( Task.INCREMENT_VERSION.name , Task.INCREMENT_VERSION.type )
		//addTask( Task.GENERATE_FONT_CLASS.name , Task.GENERATE_FONT_CLASS.type )
		//addTask( Task.AUTO_GENERATE_FONT_CLASS.name , Task.AUTO_GENERATE_FONT_CLASS.type )
		//addTask( Task.GENERATE_RESOURCE_CLASS.name , Task.GENERATE_RESOURCE_CLASS.type )
		//addTask( Task.AUTO_GENERATE_RESOURCE_CLASS.name , Task.AUTO_GENERATE_RESOURCE_CLASS.type )
	}

	@Override
	public void addExtensions()
	{
		addConfigurationExtension( platform.name.toLowerCase( ) , platform , VariantsConfigurationContainerExtension , flair as ExtensionAware )
	}

	@Override
	public final void addVariantTaskFactory( IVariantTaskFactory factory )
	{
		variantFactories.add( factory )
	}

	@Override
	public final void updateVariantTasks()
	{
		removeVariantTasks( )

		variantFactories.each {

			VariantManager.getVariants( project , platform ).each { variant -> it.create( project , variant ) }
		}

		project.tasks.getByName( Task.ASSEMBLE.name ).dependsOn( getVariantTaskNames( Group.ASSEMBLE ) )
		project.tasks.getByName( Task.COMPILE.name ).dependsOn( getVariantTaskNames( Group.COMPILE ) )
	}

	@Override
	protected void addStructures()
	{
		addStructure( new CommonStructure( ) )
		addStructure( new ClassTemplateStructure( ) )
		addStructure( new VariantStructure( ) )
	}

	protected void addVariantFactories()
	{
		addVariantTaskFactory( new AssembleTaskFactory( ) )
		addVariantTaskFactory( new CompileTaskFactory( ) )
		addVariantTaskFactory( new PackageTaskFactory( ) )
		addVariantTaskFactory( new InstallTaskFactory( ) )
		addVariantTaskFactory( new LaunchADLTaskFactory( ) )
	}

	private List<String> getVariantTaskNames( Group group )
	{
		List<String> list = new ArrayList<String>( )

		project.tasks.findAll { task -> task.group == group.name } each { list.add( it.name ) }

		return list
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
