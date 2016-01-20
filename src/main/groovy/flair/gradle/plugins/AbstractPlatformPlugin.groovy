package flair.gradle.plugins

import flair.gradle.extensions.PlatformConfigurationContainerExtension
import flair.gradle.tasks.Groups
import flair.gradle.tasks.variantFactories.*
import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformPlugin extends AbstractStructurePlugin implements IPlatformPlugin , IVariantTaskPlugin
{
	protected List<IVariantTaskFactory> variantFactories = new ArrayList<IVariantTaskFactory>( )

	protected Platforms platform

	@Override
	public Platforms getPlatform()
	{
		return platform
	}

	@Override
	public void setPlatform( Platforms platform )
	{
		this.platform = platform
	}

	@Override
	public void apply( Project project )
	{
		project.apply( plugin: BasePlugin )

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
		variantFactories.each {

			List<Variant> list = flairExtension.getAllVariants( platform )
			if( list.size( ) ) list.each { variant -> it.create( project , variant ) } else it.create( project , new Variant( project , platform ) )
		}

		//project.tasks.getByName( Tasks.ASSEMBLE.name ).dependsOn( getVariantTaskNames( Groups.ASSEMBLE ) )
		//project.tasks.getByName( Tasks.COMPILE.name ).dependsOn( getVariantTaskNames( Groups.COMPILE ) )
	}

	@Override
	public void addExtensions()
	{
		addConfigurationExtension( platform.name.toLowerCase( ) , platform , PlatformConfigurationContainerExtension , flairExtension as ExtensionAware )
	}

	protected void addVariantFactories()
	{
		addVariantTaskFactory( new AssembleTaskFactory( ) )
		addVariantTaskFactory( new CompileTaskFactory( ) )
		addVariantTaskFactory( new PackageTaskFactory( ) )
		addVariantTaskFactory( new InstallTaskFactory( ) )
		addVariantTaskFactory( new LaunchAdlTaskFactory( ) )
	}

	private List<String> getVariantTaskNames( Groups group )
	{
		List<String> list = new ArrayList<String>( )

		project.tasks.findAll { task -> task.name.startsWith( group.name ) && task.name != group.name } each { list.add( it.name ) }

		return list
	}
}
