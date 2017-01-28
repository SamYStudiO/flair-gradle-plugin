package flair.gradle.plugins

import flair.gradle.extensions.factories.IExtensionFactory

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IExtensionPlugin
{
	IExtensionFactory getExtensionFactory()
}