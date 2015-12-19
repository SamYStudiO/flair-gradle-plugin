package _appId_.utils.displayMetrics
{
	import feathers.system.DeviceCapabilities;

	/**
	 *
	 */
	internal function getDeviceDensity() : Number
	{
		return DeviceCapabilities.dpi;
	}
}
