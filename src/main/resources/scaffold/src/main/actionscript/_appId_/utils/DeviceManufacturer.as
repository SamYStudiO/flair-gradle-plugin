package _appId_.utils
{
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public final class DeviceManufacturer
	{
		/**
		 *
		 */
		public static function isWindows() : Boolean
		{
			return Capabilities.manufacturer.toLowerCase().indexOf( "windows" ) >= 0;
		}

		/**
		 *
		 */
		public static function isMacintosh() : Boolean
		{
			return Capabilities.manufacturer.toLowerCase().indexOf( "macintosh" ) >= 0;
		}

		/**
		 *
		 */
		public static function isLinux() : Boolean
		{
			return Capabilities.manufacturer.toLowerCase().indexOf( "linux" ) >= 0;
		}

		/**
		 *
		 */
		public static function isAndroid() : Boolean
		{
			return Capabilities.manufacturer.toLowerCase().indexOf( "android" ) >= 0;
		}

		/**
		 *
		 */
		public static function isIOS() : Boolean
		{
			return Capabilities.manufacturer.toLowerCase().indexOf( "ios" ) >= 0;
		}

		/**
		 *
		 */
		public static function isMobile() : Boolean
		{
			return isAndroid() || isIOS();
		}

		/**
		 *
		 */
		public static function isDesktop() : Boolean
		{
			return !isMobile();
		}

		/**
		 * @private
		 */
		public function DeviceManufacturer()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
