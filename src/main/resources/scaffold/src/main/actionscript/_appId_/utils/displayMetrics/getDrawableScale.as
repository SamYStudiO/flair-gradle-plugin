package _appId_.utils.displayMetrics
{
	import _appId_.actors.RESOURCE_MANAGER;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getDrawableScale() : Number
	{
		return RESOURCE_MANAGER.getDrawableDensityDpi() / densityDpi;
	}
}
