package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.packaging.Packaging
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class PackageTaskFactory implements VariantTaskFactory<Packaging>
{
	public Packaging create( Project project , String prefix , Platform platform , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , platform , singlePlatform , "" , "" , dependencies )
	}

	public Packaging create( Project project , String prefix , Platform platform , boolean singlePlatform , String productFlavor , String buildType , List<String> dependencies )
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

		Packaging t = project.tasks.findByName( name ) as Packaging

		if( !t ) t = project.tasks.create( name , Packaging )

		t.group = Group.PACKAGE.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType
		t.dependsOn( dependencies )

		return t
	}
}
