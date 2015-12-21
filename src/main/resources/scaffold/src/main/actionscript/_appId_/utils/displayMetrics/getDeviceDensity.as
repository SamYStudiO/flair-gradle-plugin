package _appId_.utils.displayMetrics
{
	/**
	 * Get device density that is used to compute density scale, if you want to adjust scale globally,
	 * this is the right place to do it. You also could return physical density from Density ane to scale assets
	 * to physical (real) density insteadof bucket (mdpi, hdpi, xhdpi,...) density.
	 */
	internal function getDeviceDensity() : Number
	{
		return densityDpi;
	}
}
