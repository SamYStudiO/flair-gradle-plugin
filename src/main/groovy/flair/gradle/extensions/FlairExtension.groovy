package flair.gradle.extensions

import flair.gradle.extensions.configuration.VariantConfigurationExtension
import flair.gradle.platforms.Platform
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public class FlairExtension extends VariantConfigurationExtension
{
	public static final NAME = "flair"

	public String moduleName = "app"

	public FlairExtension( String name , Project project , Platform platform )
	{
		super( name , project , platform )
	}
}
