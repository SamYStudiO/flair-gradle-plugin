package flair.gradle.tasks

import flair.gradle.extensions.configuration.IConfigurationContainerExtension
import flair.gradle.extensions.configuration.PropertyManager
import flair.gradle.platforms.Platform
import flair.gradle.plugins.AndroidPlugin
import flair.gradle.plugins.DesktopPlugin
import flair.gradle.plugins.IOSPlugin
import flair.gradle.tasks.factory.*
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.Task

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class TaskManager
{
	public
	static void updateTasks( Project project )
	{
		updateTypeVariantTasks( project , new BuildTaskFactory( ) , "build" )
		updateTypeVariantTasks( project , new PackageTaskFactory( ) , "package" )
		updateTypeVariantTasks( project , new InstallTaskFactory( ) , "install" )
		updateTypeVariantTasks( project , new LaunchTaskFactory( ) , "launch" )
	}

	public
	static void updateProcessResourceTasks( Project project )
	{
		List<Platform> platforms = getCurrentPlatform( project )

		platforms.each { platform ->

			new ProcessResourceTaskFactory( ).create( project , "process" , platform , platforms.size( ) > 1 , [ ] ) as Task
		}
	}

	public
	static void updateTypeVariantTasks( Project project , VariantTaskFactory factory , String taskPrefix )
	{
		List<Platform> platforms = getCurrentPlatform( project )

		NamedDomainObjectContainer<IConfigurationContainerExtension> allProductFlavors = PropertyManager.getRootContainer( project ).getProductFlavors( )
		NamedDomainObjectContainer<IConfigurationContainerExtension> allBuildTypes = PropertyManager.getRootContainer( project ).getBuildTypes( )

		platforms.each { platform ->

			List<IConfigurationContainerExtension> productFlavorList = new ArrayList<IConfigurationContainerExtension>( )
			List<IConfigurationContainerExtension> buildTypeList = new ArrayList<IConfigurationContainerExtension>( )

			allProductFlavors.each {
				productFlavorList.add( it )
			}

			allBuildTypes.each {
				buildTypeList.add( it )
			}

			NamedDomainObjectContainer<IConfigurationContainerExtension> platformProductFlavors = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).getProductFlavors( )
			NamedDomainObjectContainer<IConfigurationContainerExtension> platformBuildTypes = PropertyManager.getRootContainer( project ).getPlatformContainer( platform ).getBuildTypes( )

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
						buildTypeList.each { type -> factory.create( project , taskPrefix , platform , platforms.size( ) <= 1 , flavor.name , type.name , [ processResourcesTaskName ] ) as Task
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
				}
			}
			else if( buildTypeList.size( ) > 0 )
			{
				buildTypeList.each { type -> factory.create( project , taskPrefix , platform , platforms.size( ) <= 1 , "" , type.name , [ processResourcesTaskName ] ) as Task
				}
			}
		}
	}

	private static List<Platform> getCurrentPlatform( Project project )
	{
		List<Platform> list = new ArrayList<Platform>( )

		project.plugins.each { plugin ->

			if( plugin instanceof IOSPlugin && list.indexOf( Platform.IOS ) < 0 ) list.add( Platform.IOS )
			if( plugin instanceof AndroidPlugin && list.indexOf( Platform.ANDROID ) < 0 ) list.add( Platform.ANDROID )
			if( plugin instanceof DesktopPlugin && list.indexOf( Platform.DESKTOP ) < 0 ) list.add( Platform.DESKTOP )
		}

		return list
	}
}
