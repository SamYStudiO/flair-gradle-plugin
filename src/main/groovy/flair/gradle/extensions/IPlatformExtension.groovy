package flair.gradle.extensions

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IPlatformExtension extends IExtension
{
	Platforms getPlatform()

	IExtension getExtension( String name )
}