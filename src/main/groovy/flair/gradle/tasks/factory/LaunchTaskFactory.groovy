package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.launch.Launch
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchTaskFactory implements VariantTaskFactory<Launch>
{
	public Launch create( Project project , String prefix , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , null , singlePlatform , dependencies )
	}

	public Launch create( Project project , String prefix , Platform platform , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , platform , singlePlatform , "" , "" , dependencies )
	}

	public Launch create( Project project , String prefix , Platform platform , boolean singlePlatform , String productFlavor , String buildType , List<String> dependencies )
	{
		String name

		productFlavor = productFlavor ?: ""
		buildType = buildType ?: ""

		if( !singlePlatform && platform )
		{
			name = prefix + platform.name.capitalize( ) + productFlavor.capitalize( ) + buildType.capitalize( )
		}
		else
		{
			name = prefix + productFlavor.capitalize( ) + buildType.capitalize( )
		}

		Launch t = project.tasks.findByName( name ) as Launch

		if( !t ) t = project.tasks.create( name , Launch )

		t.group = Group.LAUNCH.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType
		t.dependsOn( dependencies )

		return t
	}
}
