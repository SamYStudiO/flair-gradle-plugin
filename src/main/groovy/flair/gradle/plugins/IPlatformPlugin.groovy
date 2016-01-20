package flair.gradle.plugins

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformPlugin
{
	public Platforms getPlatform()

	public void setPlatform( Platforms platform )
}
