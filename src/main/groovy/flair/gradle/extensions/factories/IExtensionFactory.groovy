package flair.gradle.extensions.factories

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IExtensionFactory<T>
{
	public T create( ExtensionAware parent , Project project )
}