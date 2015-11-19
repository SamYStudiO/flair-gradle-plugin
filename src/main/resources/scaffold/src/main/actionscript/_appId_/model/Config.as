package _appId_.model
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public final class Config
	{
		/**
		 *
		 */
		public static var FIRST_SCREEN_PATH : String = EnumScreen.HOME;
		CONFIG::DEV /**
		 *
		 */ public static var SERVER_BASE : String = "";
		CONFIG::PREPROD /**
		 *
		 */ public static var SERVER_BASE : String = "";
		CONFIG::PROD /**
		 *
		 */ public static var SERVER_BASE : String = "";

		/**
		 * @private
		 */
		public function Config()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
