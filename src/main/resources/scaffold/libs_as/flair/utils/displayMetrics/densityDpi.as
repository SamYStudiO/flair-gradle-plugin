package flair.utils.displayMetrics
{
	import feathers.system.DeviceCapabilities;

	/**
	 * Store actual density dpi bucket (mdpi 160, hdpi 240, xhdpi 320, etc...).
	 */
	public const densityDpi : int = getDensityDpiFromPhysicalDensity( DeviceCapabilities.dpi );
}
