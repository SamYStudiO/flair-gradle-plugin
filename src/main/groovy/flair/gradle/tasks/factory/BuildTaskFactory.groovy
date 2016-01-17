package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.build.Build
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class BuildTaskFactory implements VariantTaskFactory<Build>
{
	public Build create( Project project , String prefix , Platform platform , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , platform , singlePlatform , null , null , dependencies )
	}

	public Build create( Project project , String prefix , Platform platform , boolean singlePlatform , String productFlavor , String buildType , List<String> dependencies )
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

		Build t = project.tasks.findByName( name ) as Build

		if( !t ) t = project.tasks.create( name , Build )

		t.group = Group.BUILD.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType
		t.dependsOn( dependencies )

		return t
	}
}
