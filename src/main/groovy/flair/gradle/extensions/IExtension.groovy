package flair.gradle.extensions

import flair.gradle.variants.Variant
import org.gradle.api.Project

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IExtension
{
	String getName()

	Project getProject()

	IExtensionManager getExtensionManager()

	Object getProp( String property , Variant variant )

	Object getProp( String property , Variant variant , boolean returnDefaultIfNull )
}