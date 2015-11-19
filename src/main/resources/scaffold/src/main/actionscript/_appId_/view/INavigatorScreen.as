package _appId_.view
{
	import feathers.controls.ScreenNavigatorItem;

	import starling.display.DisplayObject;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public interface INavigatorScreen extends IndexedScreen
	{
		/**
		 *
		 */
		function get activeScreen() : DisplayObject

		/**
		 *
		 */
		function get activeScreenID() : String

		/**
		 *
		 */
		function get autoSizeMode() : String

		/**
		 *
		 */
		function set autoSizeMode( s : String ) : void

		/**
		 *
		 */
		function get clipContent() : Boolean

		/**
		 *
		 */
		function set clipContent( b : Boolean ) : void

		/**
		 *
		 */
		function get defaultScreenID() : String

		/**
		 *
		 */
		function get defaultScreenParams() : Object

		/**
		 *
		 */
		function get transition() : Function

		/**
		 *
		 */
		function set transition( f : Function ) : void

		/**
		 *
		 */
		function addScreen( id : String, item : ScreenNavigatorItem ) : void

		/**
		 *
		 */
		function addScreenAt( id : String, item : ScreenNavigatorItem, index : uint ) : void

		/**
		 *
		 */
		function removeScreen( id : String ) : void

		/**
		 *
		 */
		function removeScreenAt( index : uint ) : void

		/**
		 *
		 */
		function removeAllScreens() : void

		/**
		 *
		 */
		function clearScreen() : void

		/**
		 *
		 */
		function getScreen( id : String ) : ScreenNavigatorItem

		/**
		 *
		 */
		function getScreenAt( index : uint ) : ScreenNavigatorItem

		/**
		 *
		 */
		function getScreenIDAt( index : uint ) : String

		/**
		 *
		 */
		function getScreenIDs( result : Vector.<String> = null ) : Vector.<String>

		/**
		 *
		 */
		function getScreenDefaultParams( id : String ) : Object

		/**
		 *
		 */
		function setScreenDefaultParams( id : String, params : Object ) : void

		/**
		 *
		 */
		function hasScreen( id : String ) : Boolean

		/**
		 *
		 */
		function showScreen( screenID : String = null, params : Object = null ) : IndexedScreen
	}
}
