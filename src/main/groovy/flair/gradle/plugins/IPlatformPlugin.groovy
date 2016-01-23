package flair.gradle.plugins

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
interface IPlatformPlugin extends IExtensionPlugin , IStructurePlugin , IVariantTaskPlugin , IConfigurationPlugin
{
	public Platforms getPlatform()

	public void setPlatform( Platforms platform )
}
