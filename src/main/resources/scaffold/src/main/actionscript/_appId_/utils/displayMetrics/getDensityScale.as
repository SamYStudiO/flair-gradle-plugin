package _appId_.utils.displayMetrics
{
	/**
	 *
	 */
	public function getDensityScale() : Number
	{
		return getDeviceDensity() / EnumDensityDpi.DENSITY_DEFAULT;
	}
}
