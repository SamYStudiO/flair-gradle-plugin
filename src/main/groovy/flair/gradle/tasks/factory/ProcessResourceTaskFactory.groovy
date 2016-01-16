package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.others.ProcessResources
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessResourceTaskFactory implements VariantTaskFactory<ProcessResources>
{
	public ProcessResources create( Project project , String prefix , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , null , singlePlatform , dependencies )
	}

	public ProcessResources create( Project project , String prefix , Platform platform , boolean singlePlatform , List<String> dependencies )
	{
		return create( project , prefix , platform , singlePlatform , "" , "" , dependencies )
	}

	public ProcessResources create( Project project , String prefix , Platform platform , boolean singlePlatform , String productFlavor , String buildType , List<String> dependencies )
	{
		String name = prefix + ( singlePlatform ? platform.name.capitalize( ) : "" ) + "Resources"

		ProcessResources t = project.tasks.findByName( name ) as ProcessResources

		if( !t ) t = project.tasks.create( name , ProcessResources )

		t.group = Group.DEFAULT.name
		t.platform = platform
		t.productFlavor = productFlavor
		t.buildType = buildType
		t.dependsOn( dependencies )

		return t
	}
}
