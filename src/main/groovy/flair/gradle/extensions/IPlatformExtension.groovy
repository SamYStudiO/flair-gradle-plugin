package flair.gradle.extensions

import flair.gradle.utils.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IPlatformExtension extends IExtension
{
	Platform getPlatform()

	IExtension getExtension( String name )
}