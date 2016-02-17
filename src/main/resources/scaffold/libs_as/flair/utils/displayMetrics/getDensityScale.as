package flair.utils.displayMetrics
{
	/**
	 * Get density scale from default mdpi (160dp) bucket. This should be the same as using Starling.current.contentScaleFactor since
	 * starling stage is scale using this value.
	 */
	public function getDensityScale() : Number
	{
		return getDeviceDensity() / Density.DENSITY_DEFAULT;
	}
}
