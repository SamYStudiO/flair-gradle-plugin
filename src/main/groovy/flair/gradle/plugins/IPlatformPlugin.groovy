package flair.gradle.plugins

import flair.gradle.utils.Platform

/**
 * @author SamYStudiO ( contact@samystudio.net )
 */
public interface IPlatformPlugin extends IExtensionPlugin , IStructurePlugin , IVariantTaskPlugin , IConfigurationPlugin
{
	Platform getPlatform()

	void setPlatform( Platform platform )
}
