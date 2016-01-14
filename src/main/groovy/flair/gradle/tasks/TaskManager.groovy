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
	}

	public
	static void updateTypeVariantTasks( Project project , NamedDomainObjectContainer<ProductFlavorExtension> productFlavors , NamedDomainObjectContainer<BuildTypeExtension> buildTypes , TaskFactory factory )
	{
		List<Platform> platforms = getCurrentPlatform( project )

		Task t

		List<String> dependencies = new ArrayList<String>( )

		platforms.each { platform ->

			List<String> platformDependencies = new ArrayList<String>( )

			if( productFlavors && productFlavors.size( ) > 0 )
			{
				productFlavors.each { flavor ->

					if( buildTypes && buildTypes.size( ) > 0 )
					{
						buildTypes.each { type ->

							t = factory.create( project , platforms.size( ) > 1 ? platform : null , flavor.name , type.name ) as Task
							platformDependencies.add( t.name )
						}
					}
					else
					{
						t = factory.create( project , platforms.size( ) > 1 ? platform : null , flavor.name , "" ) as Task
						platformDependencies.add( t.name )
					}
				}
			}
			else if( buildTypes && buildTypes.size( ) > 0 )
			{
				buildTypes.each { type ->

					t = factory.create( project , platforms.size( ) > 1 ? platform : null , "" , type.name ) as Task
					platformDependencies.add( t.name )
				}
			}

			if( platforms.size( ) > 1 )
			{
				t = factory.create( project , platform ) as Task
				dependencies.add( t.name )
				if( platformDependencies.size(  ) > 1 ) t.dependsOn( platformDependencies )
				else dependencies.addAll( platformDependencies )
			}
		}

		t = factory.create( project ) as Task
		if( dependencies.size(  ) > 1 ) t.dependsOn( dependencies )
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
