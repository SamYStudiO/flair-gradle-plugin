package _appId_.view.core
{
	import feathers.controls.IScreen;

	import org.osflash.signals.Signal;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public interface IndexedScreen extends IScreen
	{
		/**
		 *
		 */
		function get index() : uint;

		function set index( index : uint ) : void;

		/**
		 *
		 */
		function get params() : Object;

		/**
		 *
		 */
		function get shown() : Signal;

		/**
		 *
		 */
		function get hidden() : Signal;

		/**
		 *
		 */
		function show() : void;

		/**
		 *
		 */
		function hide() : void;
	}
}
