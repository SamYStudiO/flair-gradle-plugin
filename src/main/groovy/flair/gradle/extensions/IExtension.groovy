package flair.gradle.extensions

import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IExtension
{
	String getName()

	Project getProject()

	IExtensionManager getExtensionManager()

	Object getProp( String property )

	Object getProp( String property , boolean returnDefaultIfNull )
}