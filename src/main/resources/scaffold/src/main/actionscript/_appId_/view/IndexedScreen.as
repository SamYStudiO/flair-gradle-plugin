package _appId_.view
{
	import feathers.controls.IScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public interface IndexedScreen extends IScreen
	{
		/**
		 *
		 */
		function get index() : uint;

		/**
		 *
		 */
		function set index( index : uint ) : void;

		/**
		 *
		 */
		function get params() : Object;

		/**
		 *
		 */
		function show() : void;

		/**
		 *
		 */
		function hide() : void;

		/**
		 *
		 */
		function shown() : void;

		/**
		 *
		 */
		function hidden() : void;
	}
}
