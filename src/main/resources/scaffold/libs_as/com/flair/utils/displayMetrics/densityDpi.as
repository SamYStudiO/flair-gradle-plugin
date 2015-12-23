package com.flair.utils.displayMetrics
{
	import flash.system.Capabilities;

	/**
	 * Store actual density dpi bucket (mdpi 160, hdpi 240, xhdpi 320, etc...).
	 */
	public const densityDpi : int = getDensityDpiFromPhysicalDensity( Capabilities.screenDPI );
}
