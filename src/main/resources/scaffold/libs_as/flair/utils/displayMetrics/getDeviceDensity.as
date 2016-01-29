package flair.utils.displayMetrics
{
	/**
	 * Get device density that is used to compute density scale (by default its a bucket density mdpi 160, hdpi 240, xhdpi 320, etc...).
	 * If you want to adjust scale globally, this is the right place to do it. You also could for example return physical density from Density ane
	 * to scale assets to physical (real) density insteadof bucket (mdpi 160, hdpi 240, xhdpi 320,...) density.
	 */
	internal function getDeviceDensity() : Number
	{
		return densityDpi;
	}
}
