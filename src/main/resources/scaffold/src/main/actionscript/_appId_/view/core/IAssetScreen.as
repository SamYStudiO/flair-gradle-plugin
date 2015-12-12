package _appId_.view.core
{
	import org.osflash.signals.Signal;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public interface IAssetScreen extends IShowHideScreen
	{
		/**
		 *
		 */
		function get assetProgress() : Signal
	}
}
