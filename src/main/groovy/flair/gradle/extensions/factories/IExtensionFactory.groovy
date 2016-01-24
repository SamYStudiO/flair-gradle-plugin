package flair.gradle.extensions.factories

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IExtensionFactory<T>
{
	T create( ExtensionAware parent , Project project )
}