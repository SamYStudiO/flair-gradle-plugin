package flair.gradle.plugins

import flair.gradle.extensions.factories.IExtensionFactory
import flair.gradle.extensions.factories.PlatformExtensionFactory
import flair.gradle.tasks.variantFactories.*
import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformPlugin extends AbstractPlugin implements IPlatformPlugin
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
	}

	@Override
	public IExtensionFactory getExtensionFactory()
	{
		return new PlatformExtensionFactory( platform )
	}

	@Override
	public List<IVariantTaskFactory> getVariantTaskFactories()
	{
		List<IVariantTaskFactory> list = new ArrayList<IVariantTaskFactory>( )

		list.add( new AssembleTaskFactory( ) )
		list.add( new CompileTaskFactory( ) )
		list.add( new PackageTaskFactory( ) )
		list.add( new InstallTaskFactory( ) )
		list.add( new LaunchAdlTaskFactory( ) )

		return list
	}
}
