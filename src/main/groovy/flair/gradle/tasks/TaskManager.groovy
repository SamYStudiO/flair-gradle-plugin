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
	static IVariantTask getTask( Project project , Group group , Variant variant )
	{
		return project.tasks.getByName( group.name + variant.name ) as IVariantTask
	}

	public
	static void removeVariantTasks( Project project )
	{
		Iterator<Task> iterator = project.tasks.iterator( )

		while( iterator.hasNext( ) )
		{
			Task t = iterator.next( )

			if( t.name.startsWith( "build" ) || t.name.startsWith( "package" ) || t.name.startsWith( "install" ) || t.name.startsWith( "launch" ) || t.name.startsWith( "process" ) ) iterator.remove( )
		}
	}

	public
	static void updateTasks( Project project )
	{
		removeVariantTasks( project )

		updateVariantTasks( project , new ProcessResourceTaskFactory( ) )
		updateVariantTasks( project , new BuildTaskFactory( ) )
		updateVariantTasks( project , new PackageTaskFactory( ) )
		updateVariantTasks( project , new InstallTaskFactory( ) )
		updateVariantTasks( project , new LaunchTaskFactory( ) )
	}

	public
	static void updateVariantTasks( Project project , VariantTaskFactory factory )
	{
		List<Platform> platforms = PluginManager.getCurrentPlatforms( project )

		platforms.each { platform ->
			VariantManager.getVariants( project , platform ).each { variant -> factory.create( project , variant ) as Task
			}
		}

		/*return

		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> allProductFlavors = PropertyManager.getRootContainer( project ).getProductFlavors( )
		NamedDomainObjectContainer<IVariantConfigurationContainerExtension> allBuildTypes = PropertyManager.getRootContainer( project ).getBuildTypes( )

		platforms.each { platform ->

			List<IVariantConfigurationContainerExtension> productFlavorList = new ArrayList<IVariantConfigurationContainerExtension>( )
			List<IVariantConfigurationContainerExtension> buildTypeList = new ArrayList<IVariantConfigurationContainerExtension>( )

			allProductFlavors.each {
				productFlavorList.add( it )
			}

			allBuildTypes.each {
				buildTypeList.add( it )
			}

			NamedDomainObjectContainer<IVariantConfigurationContainerExtension> platformProductFlavors = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).getProductFlavors( )
			NamedDomainObjectContainer<IVariantConfigurationContainerExtension> platformBuildTypes = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).getBuildTypes( )

			platformProductFlavors.each {
				productFlavorList.add( it )
			}

			platformBuildTypes.each {
				buildTypeList.add( it )
			}

			String platformName = platforms.size( ) > 1 ? platform.name.capitalize( ) : ""
			String processResourcesTaskName = "process" + platformName + "Resources"

			if( productFlavorList.size( ) > 0 )
			{
				productFlavorList.each { flavor ->
					if( buildTypeList.size( ) > 0 )
					{
						buildTypeList.each { type ->

							factory.create( project , taskPrefix , platform , platforms.size( ) <= 1 , flavor.name , type.name , [ processResourcesTaskName ] ) as Task

							createVariantDirectory( project , type.name )
						}

						if( platforms.size( ) <= 1 )
						{
							Task t = project.tasks.findByName( taskPrefix + flavor.name.capitalize( ) )
							if( t )
							{
								project.tasks.remove( t )
							}
						}
						else
						{
							Task t = project.tasks.findByName( taskPrefix + platform.name.capitalize( ) + flavor.name.capitalize( ) )
							if( t )
							{
								project.tasks.remove( t )
							}
						}
					}
					else
					{
						factory.create( project , taskPrefix , platform , platforms.size( ) <= 1 , flavor.name , "" , [ processResourcesTaskName ] ) as Task
					}

					createVariantDirectory( project , flavor.name )
				}
			}
			else if( buildTypeList.size( ) > 0 )
			{
				buildTypeList.each { type ->

					factory.create( project , taskPrefix , platform , platforms.size( ) <= 1 , "" , type.name , [ processResourcesTaskName ] ) as Task

					createVariantDirectory( project , type.name )
				}
			}
		}*/
	}
}
