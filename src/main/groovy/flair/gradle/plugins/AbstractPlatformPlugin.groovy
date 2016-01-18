package flair.gradle.plugins

import flair.gradle.extensions.VariantsConfigurationContainerExtension
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
		project.apply( plugin: "flair.base" )

		super.apply( project )

		addVariantFactories( )

		project.afterEvaluate {
			updateVariantTasks( )
		}
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
	public void addExtensions()
	{
		addConfigurationExtension( platform.name.toLowerCase( ) , platform , VariantsConfigurationContainerExtension , flairExtension as ExtensionAware )
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
		Iterator<org.gradle.api.Task> iterator = project.tasks.findAll {
			it instanceof IVariantTask && ( it as IVariantTask ).variant.platform == platform
		}.iterator( )

		while( iterator.hasNext( ) )
		{
			project.tasks.remove( iterator.next( ) )
		}
	}
}
