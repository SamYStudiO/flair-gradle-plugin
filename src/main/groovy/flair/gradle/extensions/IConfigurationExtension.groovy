package flair.gradle.extensions

import flair.gradle.variants.Platforms
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IConfigurationExtension
{
	public String getName()

	public Project getProject()

	public Platforms getPlatform()

	public IPlatformExtensionManager getExtensionManager()

	public Object getProp( String property )

	public Object getProp( String property , boolean returnDefaultIfNull )
}