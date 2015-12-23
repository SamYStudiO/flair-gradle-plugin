package _appId_.view
{
	/**
	 * Constants for all screen ids
	 */
	public final class EnumScreen
	{
		/**
		 * Top root screen (StarlingMain), DO NOT remove
		 */
		public static const MAIN : String = "main";

		/**
		 * Example screen (you may modify or remove)
		 */
		public static const HOME : String = "home";

		/**
		 * Example screen (you may modify or remove)
		 */
		public static const OTHER_SCREEN : String = "otherScreen";

		/**
		 * @private
		 */
		public function EnumScreen()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
