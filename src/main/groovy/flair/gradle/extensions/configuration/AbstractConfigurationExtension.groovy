package flair.gradle.extensions.configuration

import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractConfigurationExtension extends AbstractExtension implements IConfigurationExtension
{
	public AbstractConfigurationExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}

	@Override
	public Object getProp( String property )
	{
		getProp( property , false )
	}

	@Override
	public abstract Object getProp( String property , boolean returnDefaultIfNull )
}
