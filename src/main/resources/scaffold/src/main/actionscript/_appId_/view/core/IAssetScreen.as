package _appId_.view.core
{
	import org.osflash.signals.Signal;

	/**
	 *
	 */
	public interface IAssetScreen extends IShowHideScreen
	{
		/**
		 * Get signal to keep track of asset loading progression
		 */
		function get assetProgress() : Signal
	}
}
