package flair.gradle.extensions.configuration.factory;

import flair.gradle.extensions.configuration.variants.BuildTypeExtension;
import org.gradle.api.NamedDomainObjectFactory;
import org.gradle.api.Project;

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class BuildTypeFactory implements NamedDomainObjectFactory< BuildTypeExtension >
{
	private final Project project

	public BuildTypeFactory( Project project )
	{
		this.project = project
	}

	@Override
	public BuildTypeExtension create( String name )
	{
		return new BuildTypeExtension( name, project )
	}
}
