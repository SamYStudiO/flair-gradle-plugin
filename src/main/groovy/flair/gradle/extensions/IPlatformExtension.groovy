package flair.gradle.extensions

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformExtension extends IExtension
{
	public Platforms getPlatform()

	public IExtension getExtension( String name )

	public IExtension getAdl()

	public IExtension getAppDescriptor()
}