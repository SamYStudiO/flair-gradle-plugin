package flair.controls
{
	import feathers.controls.IScreen;

	import org.osflash.signals.Signal;

	/**
	 *
	 */
	public interface IResourceScreen extends IScreen
	{
		/**
		 * Get signal to keep track of asset loading progression
		 */
		function get assetProgress() : Signal
	}
}
