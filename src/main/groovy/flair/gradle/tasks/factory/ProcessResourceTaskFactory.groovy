package flair.gradle.tasks.factory

import flair.gradle.platforms.Platform
import flair.gradle.tasks.Group
import flair.gradle.tasks.others.ProcessResources
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ProcessResourceTaskFactory implements TaskFactory<ProcessResources>
{
	public ProcessResources create( Project project , Platform platform , boolean singlePlatform , List<String> dependencies )
	{
		String name = "process" + ( singlePlatform ? platform.name.capitalize( ) : "" ) + "Resources"

		ProcessResources t = project.tasks.findByName( name ) as ProcessResources

		if( !t ) t = project.tasks.create( name , ProcessResources )

		t.group = Group.DEFAULT.name
		t.platform = platform
		t.dependsOn( dependencies )

		return t
	}
}
