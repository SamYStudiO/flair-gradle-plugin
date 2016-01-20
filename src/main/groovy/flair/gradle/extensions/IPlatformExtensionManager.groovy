package flair.gradle.extensions

import flair.gradle.variants.Platforms
import flair.gradle.variants.Variant
import flair.gradle.watcher.Watcher
import org.gradle.api.NamedDomainObjectContainer

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformExtensionManager
{
	public Watcher getWatcher()

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getAllProductFlavors()

	public NamedDomainObjectContainer<IVariantConfigurationContainerExtension> getAllBuildTypes()

	public List<Variant> getAllVariants()

	public List<Variant> getAllVariants( Platforms platform )

	public Object getFlairProperty( String name )

	public Object getFlairProperty( String name , Platforms platform )

	public Object getFlairProperty( String name , Variant variant )

	public Object getFlairProperty( String configurationName , String name )

	public Object getFlairProperty( String configurationName , String name , Platforms platform )

	public Object getFlairProperty( String configurationName , String name , Variant variant )
}