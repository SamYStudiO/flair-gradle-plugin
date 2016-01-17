package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.install.Install
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class InstallTaskFactory implements VariantTaskFactory<Install>
{
	public Install create( Project project , String prefix , Platform platform , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , platform , singlePlatform , "" , "" , dependencies )
	}

	public Install create( Project project , String prefix , Platform platform , boolean singlePlatform , String productFlavor , String buildType , List<String> dependencies )
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

		Install t = project.tasks.findByName( name ) as Install

		if( !t ) t = project.tasks.create( name , Install )

		t.group = Group.INSTALL.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType
		t.dependsOn( dependencies )

		return t
	}
}
