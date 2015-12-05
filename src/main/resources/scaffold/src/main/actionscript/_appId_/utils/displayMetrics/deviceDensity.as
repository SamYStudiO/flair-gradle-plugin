package _appId_.utils.displayMetrics
{
	import feathers.system.DeviceCapabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	internal function deviceDensity() : Number
	{
		return DeviceCapabilities.dpi;
	}
}
