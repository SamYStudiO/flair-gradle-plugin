package flair.gradle.plugins

import flair.gradle.dependencies.Config
import flair.gradle.extensions.factories.IExtensionFactory
import flair.gradle.extensions.factories.PlatformExtensionFactory
import flair.gradle.structures.IStructure
import flair.gradle.structures.PlatformStructure
import flair.gradle.tasks.variantFactories.*
import flair.gradle.utils.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractPlatformPlugin extends AbstractPlugin implements IPlatformPlugin
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
	public List<IStructure> getStructures()
	{
		List<IStructure> list = new ArrayList<IStructure>( )
		list.add( new PlatformStructure( platform ) )

		return list
	}

	@Override
	public List<Config> getConfigurations()
	{
		return Config.values( ).findAll {
			it.name.toLowerCase( ).indexOf( platform.name.toLowerCase( ) ) == 0
		}
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
		list.add( new ProcessAssetsTaskFactory( ) )
		list.add( new ProcessResourcesTaskFactory( ) )
		list.add( new ProcessSplashScreensTaskFactory( ) )
		list.add( new ProcessIconsTaskFactory( ) )
		list.add( new ProcessExtensionsTaskFactory( ) )
		list.add( new ProcessAppDescriptorTaskFactory( ) )
		list.add( new ProcessClassesTaskFactory( ) )
		list.add( new ProcessAsLibrariesTaskFactory( ) )
		list.add( new ProcessLibrariesTaskFactory( ) )
		list.add( new ProcessSigningTaskFactory( ) )
		list.add( new PreparePackageTaskFactory( ) )

		return list
	}
}
