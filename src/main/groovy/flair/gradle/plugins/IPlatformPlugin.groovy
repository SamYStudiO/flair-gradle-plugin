package flair.gradle.plugins

import flair.gradle.variants.Platforms

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IPlatformPlugin extends IExtensionPlugin , IStructurePlugin , IVariantTaskPlugin , IConfigurationPlugin
{
	Platforms getPlatform()

	void setPlatform( Platforms platform )
}
