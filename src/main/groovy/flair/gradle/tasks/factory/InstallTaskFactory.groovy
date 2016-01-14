package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.install.Install
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class InstallTaskFactory implements TaskFactory<Install>
{
	public Install create( Project project )
	{
		return create( project , null )
	}

	public Install create( Project project , Platform platform )
	{
		return create( project , platform , "" , "" )
	}

	public Install create( Project project , Platform platform , String productFlavor , String buildType )
	{
		String name

		productFlavor = productFlavor ?: ""
		buildType = buildType ?: ""

		if( platform )
		{
			name = "install" + platform.name.capitalize( ) + productFlavor.capitalize( ) + buildType.capitalize( )
		}
		else
		{
			name = "install" + productFlavor.capitalize( ) + buildType.capitalize( )
		}

		Install t = project.tasks.findByName( name ) as Install

		if( !t ) t = project.tasks.create( name , Install )

		t.group = Group.INSTALL.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType

		return t
	}
}
