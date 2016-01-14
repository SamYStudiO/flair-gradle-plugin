package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.packaging.Packaging
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PackageTaskFactory implements TaskFactory<Packaging>
{
	public Packaging create( Project project )
	{
		return create( project , null )
	}

	public Packaging create( Project project , Platform platform )
	{
		return create( project , platform , "" , "" )
	}

	public Packaging create( Project project , Platform platform , String productFlavor , String buildType )
	{
		String name

		productFlavor = productFlavor ?: ""
		buildType = buildType ?: ""

		if( platform )
		{
			name = "package" + platform.name.capitalize( ) + productFlavor.capitalize( ) + buildType.capitalize( )
		}
		else
		{
			name = "package" + productFlavor.capitalize( ) + buildType.capitalize( )
		}

		Packaging t = project.tasks.findByName( name ) as Packaging

		if( !t ) t = project.tasks.create( name , Packaging )

		t.group = Group.PACKAGE.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType

		return t
	}
}
