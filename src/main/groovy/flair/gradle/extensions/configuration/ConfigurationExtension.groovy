package flair.gradle.extensions.configuration

import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ConfigurationExtension extends AbstractExtension
{
	protected AppDescriptorExtension appDescriptor = new AppDescriptorExtension( )

	protected ADLExtension adl = new ADLExtension( )

	public ConfigurationExtension( String name , Project project )
	{
		super( name , project )
	}

	public void appDescriptor( Action<AppDescriptorExtension> action )
	{
		action.execute( appDescriptor )
	}

	public void adl( Action<ADLExtension> action )
	{
		action.execute( adl )
	}
}
