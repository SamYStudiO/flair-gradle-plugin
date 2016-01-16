package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import org.gradle.api.Action
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class ConfigurationContainerExtension extends AbstractConfigurationExtension implements IConfigurationContainerExtension
{
	protected AppDescriptorExtension appDescriptor

	protected ADLExtension adl

	public ConfigurationContainerExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )

		appDescriptor = new AppDescriptorExtension( ConfigurationExtension.APP_DESCRIPTOR.name , project , platform )
		adl = new ADLExtension( ConfigurationExtension.ADL.name , project , platform )
	}

	public void appDescriptor( Action<AppDescriptorExtension> action )
	{
		action.execute( appDescriptor )
	}

	public void adl( Action<ADLExtension> action )
	{
		action.execute( adl )
	}

	@Override
	public IConfigurationExtension getConfiguration( String name )
	{
		return this[ name ] as IConfigurationExtension
	}

	@Override
	public Object getProp( String property , boolean defaultIfNull )
	{
		return null
	}
}
