package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IExtension
{
	public String getName()

	public Project getProject()

	public IExtensionManager getExtensionManager()

	public Object getProp( String property )

	public Object getProp( String property , boolean returnDefaultIfNull )
}