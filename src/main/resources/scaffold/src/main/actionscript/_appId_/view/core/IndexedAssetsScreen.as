package _appId_.view.core
{
	import org.osflash.signals.Signal;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public interface IndexedAssetsScreen extends IndexedScreen
	{
		/**
		 *
		 */
		function get isReady() : Boolean

		/**
		 *
		 */
		function get assetsProgress() : Signal

		/**
		 *
		 */
		function get assetsComplete() : Signal

		/**
		 *
		 */
		function get assetsError() : Signal
	}
}
