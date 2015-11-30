package _appId_.utils.displayMetrics
{
	import feathers.system.DeviceCapabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDensity() : Number
	{
		return DeviceCapabilities.dpi * densityScaleOffset;
	}
}
