package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.launch.Launch
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class LaunchTaskFactory implements TaskFactory<Launch>
{
	public Launch create( Project project )
	{
		return create( project , null )
	}

	public Launch create( Project project , Platform platform )
	{
		return create( project , platform , "" , "" )
	}

	public Launch create( Project project , Platform platform , String productFlavor , String buildType )
	{
		String name

		productFlavor = productFlavor ?: ""
		buildType = buildType ?: ""

		if( platform )
		{
			name = "launch" + platform.name.capitalize( ) + productFlavor.capitalize( ) + buildType.capitalize( )
		}
		else
		{
			name = "launch" + productFlavor.capitalize( ) + buildType.capitalize( )
		}

		Launch t = project.tasks.findByName( name ) as Launch

		if( !t ) t = project.tasks.create( name , Launch )

		t.group = Group.LAUNCH.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType

		return t
	}
}
