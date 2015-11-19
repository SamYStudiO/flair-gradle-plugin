package _appId_.utils
{
	import flash.net.URLVariables;
	import flash.system.Capabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public final class DeviceInfos
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
		 *
		 */
		public static function dpi() : uint
		{
			var urlVariables : URLVariables = new URLVariables();
			urlVariables.decode( Capabilities.serverString );
			return parseInt( urlVariables.DP, 10 );
		}

		/**
		 * @private
		 */
		public function DeviceInfos()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
