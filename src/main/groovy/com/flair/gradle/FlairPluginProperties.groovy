package com.flair.gradle

/**
 * @author SamYStudiO on 08/11/2015.
 */
public class FlairPluginProperties
{
	public String moduleName = "app"

	public String appId = ""

	public String appName = ""

	public String appVersion = "0.0.0"

	public Boolean appFullScreen = true

	public String appAspectRatio = "any"

	public Boolean appAutoOrient = true

	public Boolean appDepthAndStencil = false

	public String defaultLocale = "en"

	public Boolean addGenerateAtlasesTaskToRunConfiguration = true

	public Boolean generateATFTexturesFromAtlases = true

	public String iosExcludeResources = "drawable*-ldpi*/**,drawable*-mdpi*/**,drawable*-hdpi*/**,drawable*-xxxhdpi*/**"

	public String androidExcludeResources = "drawable*-ldpi*/**,drawable*-xxxhdpi*/**"

	public String desktopExcludeResources = "drawable*-ldpi*/**,drawable*-hdpi*/**,drawable*-xxhdpi*/**,drawable*-xxxhdpi*/**"
}
