package flair.gradle.extensions

import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public abstract class AbstractConfigurationExtension extends AbstractExtension implements IConfigurationExtension
{
	public AbstractConfigurationExtension( String name , Project project , Platforms platform )
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
