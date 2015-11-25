package _appId_.utils
{
	import _appId_.utils.string.digitFormat;

	/**
	 * TimeFormatter convert milliseconds Number to string representation using formats (hh:mm:SS for example).
	 *
	 * <p>Patterns list :</p>
	 *
	 * <li>w    : week with one or two digit (8)</li>
	 * <li>ww    : week with two digit (08)</li>
	 * <li>d    : day day with one or two digits (9)</li>
	 * <li>dd    : day day with two digits (09)</li>
	 * <li>h    : hour with one or two digits (1)</li>
	 * <li>hh    : hour with two digits (01)</li>
	 * <li>m    : minute with one or two digits (8)</li>
	 * <li>mm    : minute with two digits (08)</li>
	 * <li>S    : second with one or two digits (5)</li>
	 * <li>SS    : second with two digits (05)</li>
	 * <li>s    : millisecond with one or two digits or three digits (3)</li>
	 * <li>ss    : millisecond with three digits (03)</li>
	 *
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class TimeFormatter
	{
		/**
		 * GGet the number of milliseconds in the specified milliseconds.
		 * @param milliseconds The milliseconds where retrive milliseconds.
		 * @param overflow A Boolean that indicates if overflow which could be converted in second or bigger pattern is returned. If overflow is false max value is 999.
		 * @param round A Boolean that indicates if value is rounded.
		 * @return The number of milliseconds according specified arguments.
		 */
		public static function getMilliseconds( milliseconds : Number , overflow : Boolean = false , round : Boolean = false ) : Number
		{
			var n : Number = overflow ? milliseconds : milliseconds % 1000;

			return round ? Math.round( n ) : Math.floor( n );
		}

		/**
		 * Get the number of seconds in the specified milliseconds.
		 * @param milliseconds The milliseconds where retrive seconds.
		 * @param overflow A Boolean that indicates if overflow which could be converted in minutes or bigger pattern is returned. If overflow is false max value is 59.
		 * @param round A Boolean that indicates if value is rounded.
		 * @return The number of seconds according specified arguments.
		 */
		public static function getSeconds( milliseconds : Number , overflow : Boolean = false , round : Boolean = false ) : Number
		{
			var n : Number = overflow ? milliseconds / 1000 : milliseconds / 1000 % 60;

			return round ? Math.round( n ) : Math.floor( n );
		}

		/**
		 * Get the number of minutes in the specified milliseconds.
		 * @param milliseconds The milliseconds where retrive minutes.
		 * @param overflow A Boolean that indicates if overflow which could be converted in hours or bigger pattern is returned. If overflow is false max value is 59.
		 * @param round A Boolean taht indicates if value is rounded.
		 * @return The number of minutes according specified arguments.
		 */
		public static function getMinutes( milliseconds : Number , overflow : Boolean = false , round : Boolean = false ) : Number
		{
			var n : Number = overflow ? milliseconds / 60000 : milliseconds / 60000 % 60;

			return round ? Math.round( n ) : Math.floor( n );
		}

		/**
		 * Get the number of hours in the specified milliseconds.
		 * @param milliseconds The milliseconds where retrive hours.
		 * @param overflow A Boolean that indicates if overflow which could be converted in days or bigger pattern is returned. If overflow is false max value is 23.
		 * @param round A Boolean taht indicates if value is rounded.
		 * @return The number of hours according specified arguments.
		 */
		public static function getHours( milliseconds : Number , overflow : Boolean = false , round : Boolean = false ) : Number
		{
			var n : Number = overflow ? milliseconds / 3600000 : milliseconds / 3600000 % 24;

			return round ? Math.round( n ) : Math.floor( n );
		}

		/**
		 * Get the number of days in the specified milliseconds.
		 * @param milliseconds The milliseconds where retrive days.
		 * @param overflow A Boolean that indicates if overflow which could be converted in weeks or bigger pattern is returned. If overflow is false max value is 6.
		 * @param round A Boolean taht indicates if value is rounded.
		 * @return The number of days according specified arguments.
		 */
		public static function getDays( milliseconds : Number , overflow : Boolean = false , round : Boolean = false ) : Number
		{
			var n : Number = overflow ? milliseconds / 86400000 : milliseconds / 86400000 % 7;

			return round ? Math.round( n ) : Math.floor( n );
		}

		/**
		 * Get the number of weeks in the specified milliseconds.
		 * @param milliseconds The milliseconds where retrive weeks.
		 * @param round A Boolean that indicates if value is rounded.
		 * @return The number of weeks according specified arguments.
		 */
		public static function getWeeks( milliseconds : Number , round : Boolean = false ) : Number
		{
			return round ? Math.floor( milliseconds / 604800000 ) : Math.floor( milliseconds / 604800000 );
		}

		/**
		 * Convert a milliseconds Number to a string representation.
		 * @param milliseconds The milliseconds Number to convert.
		 * @param format The format to use with conversion.
		 * @param escapeChar A Char to used to esape format patterns.
		 * @return The Number converted to a string representation.
		 */
		public static function format( milliseconds : Number , format : String , round : Boolean = false , overflowPatterns : Array = null , escapeChar : String = "@" ) : String
		{
			var i : uint = 0;
			var l : uint = format.length;
			var result : String = "";
			var patterns : Array = __getPatterns( format , escapeChar );

			while( i < l )
			{
				var char : String = format.charAt( i );
				var pattern : String = char;

				if( char == escapeChar )
				{
					result += format.charAt( ++i );
					++i;
					continue;
				}

				while( format.charAt( ++i ) == char && i < l ) pattern += char;

				result += __convertPattern( pattern , Math.round( milliseconds ) , round ? patterns[ patterns.length - 1 ] : null , overflowPatterns || [ patterns[ 0 ] ] );
			}

			return result;
		}

		/**
		 * Convert a string time representation to a milliseconds Number.
		 * @param from The string time representation.
		 * @param format The patterns format to used.
		 * @param caseSensitive A boolean that indicates if format is case sensitive.
		 * @return The milliseconds Number from string representation. If conversion is impossible the value is NaN.
		 */
		public static function getTimeFrom( from : String , format : String , caseSensitive : Boolean = false ) : Number
		{
			var i : uint = 0;
			var l : uint = format.length;
			var searchIndex : uint = 0;
			var w : int = 0;
			var d : int = 0;
			var h : int = 0;
			var mi : int = 0;
			var s : int = 0;
			var ms : int = 0;
			var str : String;

			while( i < l )
			{
				var char : String = format.charAt( i );
				var pattern : String = char;

				while( format.charAt( ++i ) == char && i < l ) pattern += char;

				switch( pattern )
				{
					case "ww" :
						w = int( from.substr( searchIndex , 2 ) );
						searchIndex += 2;
						break;
					case "dd" :
						str = from.substr( searchIndex , 2 );
						d = int( str );
						if( d < 0 || d > 6 || str.length != 2 ) return NaN;
						searchIndex += 2;
						break;
					case "hh" :
						str = from.substr( searchIndex , 2 );
						h = int( str );
						if( h < 0 || h > 23 || str.length != 2 ) return NaN;
						searchIndex += 2;
						break;
					case "mm" :
						str = from.substr( searchIndex , 2 );
						mi = int( str );
						if( mi < 0 || mi > 59 || str.length != 2 ) return NaN;
						searchIndex += 2;
						break;
					case "SS" :
						str = from.substr( searchIndex , 2 );
						s = int( str );
						if( s < 0 || s > 59 || str.length != 2 ) return NaN;
						searchIndex += 2;
						break;
					case "ss" :
						str = from.substr( searchIndex , 3 );
						ms = int( str );
						if( ms < 0 || ms > 999 || str.length != 3 ) return NaN;
						searchIndex += 3;
						break;
					default    :
						if( caseSensitive )
						{
							if( from.substr( searchIndex , pattern.length ) != pattern ) return NaN;
						}
						else
						{
							if( from.substr( searchIndex , pattern.length ).toLowerCase() != pattern.toLowerCase() ) return NaN;
						}
						searchIndex += pattern.length;
						break;
				}
			}

			// check there is no more char from source
			if( from.length > searchIndex ) return NaN;

			return w * 604800000 + d * 86400000 + h * 3600000 + mi * 60000 + s * 1000 + ms;
		}

		/**
		 * @private
		 */
		protected static function __getPatterns( format : String , escapeChar : String = "@" ) : Array
		{
			var i : uint = 0;
			var l : uint = format.length;
			var a : Array = [];

			while( i < l )
			{
				var char : String = format.charAt( i );
				var pattern : String = char;
				var isPattern : Boolean;

				if( char == escapeChar )
				{
					++i;
					continue;
				}

				while( format.charAt( ++i ) == char && i < l ) pattern += char;

				isPattern = __PATTERNS.indexOf( pattern ) >= 0;

				while( !isPattern && pattern.length > 1 )
				{
					pattern = pattern.substr( 0 , pattern.length - 1 );
					isPattern = __PATTERNS.indexOf( pattern ) >= 0;
					--i;
				}

				if( isPattern ) a.push( pattern );
			}

			return a;
		}

		/**
		 *
		 */
		private static function __convertPattern( pattern : String , milliseconds : Number , roundPattern : String = null , overflowPatterns : Array = null ) : String
		{
			switch( true )
			{
				case pattern == "w"        :
					return String( getWeeks( milliseconds ) );
				case pattern == "ww"    :
					return digitFormat( getWeeks( milliseconds ) );
				case pattern == "d"        :
					return String( getDays( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "dd"    :
					return digitFormat( getDays( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "h"        :
					return String( getHours( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "hh"    :
					return digitFormat( getHours( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "m"        :
					return String( getMinutes( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "mm"    :
					return digitFormat( getMinutes( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "S"        :
					return String( getSeconds( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "SS"    :
					return digitFormat( getSeconds( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "s"        :
					return String( getMilliseconds( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) );
				case pattern == "ss"    :
					return digitFormat( getMilliseconds( milliseconds , overflowPatterns.indexOf( pattern ) >= 0 , roundPattern == pattern ) , 3 );
				default                    :
					var l : uint = pattern.length;
					return l > 1 ? __convertPattern( pattern.substr( 0 , l - 1 ) , milliseconds , roundPattern , overflowPatterns ) + pattern.substr( l - 1 ) : pattern;
			}
		}

		/**
		 * @private
		 */
		public function TimeFormatter()
		{
			throw new Error( this + " cannot be instantiated" );
		}

		/**
		 * @private
		 */
		protected static const __PATTERNS : Array = [ "w" , "ww" , "d" , "dd" , "h" , "hh" , "m" , "mm" , "S" , "SS" , "s" , "ss" ];
	}
}
