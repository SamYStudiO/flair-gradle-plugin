package _appId_.utils.displayMetrics
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDensityScale() : Number
	{
		return deviceDensity() / DENSITY_MDPI;
	}
}
