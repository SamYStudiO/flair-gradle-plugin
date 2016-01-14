package flair.gradle.tasks

import flair.gradle.extensions.configuration.variants.BuildTypeExtension
import flair.gradle.extensions.configuration.variants.ProductFlavorExtension
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
	static void updateVariantTasks( Project project , NamedDomainObjectContainer<ProductFlavorExtension> productFlavors = null , NamedDomainObjectContainer<BuildTypeExtension> buildTypes = null )
	{
		updateTypeVariantTasks( project , productFlavors , buildTypes , new BuildTaskFactory( ) )
		updateTypeVariantTasks( project , productFlavors , buildTypes , new PackageTaskFactory( ) )
		updateTypeVariantTasks( project , productFlavors , buildTypes , new InstallTaskFactory( ) )
		updateTypeVariantTasks( project , productFlavors , buildTypes , new LaunchTaskFactory( ) )
		updateProcessResourceTasks( project , new ProcessResourceTaskFactory( ) )
	}

	public
	static void updateProcessResourceTasks( Project project , TaskFactory factory )
	{
		List<Platform> platforms = getCurrentPlatform( project )

		platforms.each { platform ->

			factory.create( project , platform , platforms.size( ) > 1 , [ ] ) as Task
		}
	}

	public
	static void updateTypeVariantTasks( Project project , NamedDomainObjectContainer<ProductFlavorExtension> productFlavors , NamedDomainObjectContainer<BuildTypeExtension> buildTypes , VariantTaskFactory factory )
	{
		List<Platform> platforms = getCurrentPlatform( project )
		List<String> dependencies = new ArrayList<String>( )
		Task t

		platforms.each { platform ->

			String platformName = platforms.size( ) > 1 ? platform.name.capitalize( ) : ""
			String proccessResourcesTaskName = "process" + platformName + "Resources"
			List<String> platformDependencies = new ArrayList<String>( )

			if( productFlavors && productFlavors.size( ) > 0 )
			{
				productFlavors.each { flavor ->

					if( buildTypes && buildTypes.size( ) > 0 )
					{
						buildTypes.each { type ->

							t = factory.create( project , platforms.size( ) > 1 ? platform : null , platforms.size( ) > 1 , flavor.name , type.name , [ proccessResourcesTaskName ] ) as Task
							platformDependencies.add( t.name )
						}
					}
					else
					{
						t = factory.create( project , platforms.size( ) > 1 ? platform : null , platforms.size( ) > 1 , flavor.name , "" , [ proccessResourcesTaskName ] ) as Task
						platformDependencies.add( t.name )
					}
				}
			}
			else if( buildTypes && buildTypes.size( ) > 0 )
			{
				buildTypes.each { type ->

					t = factory.create( project , platforms.size( ) > 1 ? platform : null , platforms.size( ) > 1 , "" , type.name , [ proccessResourcesTaskName ] ) as Task
					platformDependencies.add( t.name )
				}
			}

			if( platforms.size( ) > 1 )
			{
				t = factory.create( project , platform , platforms.size( ) > 1 , platformDependencies.size( ) > 1 ? platformDependencies : [ ] ) as Task
				dependencies.add( t.name )
			}
			else dependencies = platformDependencies.clone( ) as List<String>
		}

		t = factory.create( project , platforms.size( ) > 1 , dependencies.size( ) > 1 ? dependencies : [ ] ) as Task
	}

	protected static List<Platform> getCurrentPlatform( Project project )
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
