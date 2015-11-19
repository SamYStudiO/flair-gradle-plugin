package _appId_.utils
{
	import _appId_.utils.number.isInteger;
	import _appId_.utils.string.digitFormat;

	/**
	 * DateFomatter convert Date objects to string representation using formats (yyyy/MM/dd for example).
	 *
	 * <p>Patterns list :</p>
	 *
	 * <li>yy    : year with two digits (08)</li>
	 * <li>yyyy    : year with four digits (2008)</li>
	 * <li>M    : month with one or two digits (2)</li>
	 * <li>MM    : month two digits (02)</li>
	 * <li>MMM    : month using short month name (Feb)</li>
	 * <li>MMMM    : month using full month name (February)</li>
	 * <li>d    : month day with one or two digits (9)</li>
	 * <li>dd    : month day with two digits (09)</li>
	 * <li>D    : day of the year using one, two or three digits (12)</li>
	 * <li>DD    : day of the year using three digits (012)</li>
	 * <li>E    : week day using short week name (Sat)</li>
	 * <li>EE    : week day using full week name (Saturday)</li>
	 * <li>w    : week of the year with one or two digits (9)</li>
	 * <li>ww    : week of the year with two digits (09)</li>
	 * <li>h    : hour in range 0-12 with one or two digits (1)</li>
	 * <li>hh    : hour in range 0-12 with two digits (01)</li>
	 * <li>H    : hour in range 0-23 with one or two digits (13)</li>
	 * <li>HH    : hour in range 0-23 with two digits (13)</li>
	 * <li>m    : minute with one or two digits (8)</li>
	 * <li>mm    : minute with two digits (08)</li>
	 * <li>s    : second with one or two digits (5)</li>
	 * <li>ss    : second with two digits (05)</li>
	 * <li>S    : millisecond with one, two or three digits (3)</li>
	 * <li>SS    : millisecond with three digits (003)</li>
	 * <li>aa    : meridian "am" or "pm" in lower case</li>
	 * <li>AA    : meridian "AM" or "PM" in upper case</li>
	 * <li>o    : timezone (+0100)</li>
	 * <li>O    : timezone with ":" separator between hours and minutes (+01:00)</li>
	 *
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public final class DateFormatter
	{
		/**
		 * An Array of full month names.
		 */
		public static var MONTHS : Array = [ "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December" ];

		/**
		 * An Array of short month names (3 chars).
		 */
		public static var MONTHS_SHORT : Array = [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ];

		/**
		 * An Array of full week day names.
		 */
		public static var WEEKDAY : Array = [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ];

		/**
		 * An Array of short week day names (3 chars).
		 */
		public static var WEEKDAY_SHORT : Array = [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ];

		/**
		 * Convert a Date object to a string representation.
		 * @param d The date object to convert.
		 * @param format The format to use with conversion.
		 * @param escapeChar A Char to used to esape format patterns.
		 * @return The date converted to a string representation.
		 */
		public static function format( d : Date, format : String, escapeChar : String = "@" ) : String
		{
			var i : uint = 0;
			var l : uint = format.length;
			var result : String = "";

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

				result += __convertPattern( pattern, d );
			}

			return result;
		}

		/**
		 * Convert a string date representation to a Date object using specified format.
		 * @param from The string date representation.
		 * @param format The patterns format to used.
		 * @param caseSensitive A boolean that indicates if format is case sensitive.
		 * @param autoAddOverflow A Boolean that indicates if an overflow value (a value that does not match a pattern range > month 13 out of range 1-12) is allowed.
		 * @return The date object from string representation. If conversion is impossible the value is null.
		 */
		public static function getDateFrom( from : String, format : String, caseSensitive : Boolean = false, autoAddOverflow : Boolean = false ) : Date
		{
			var i : uint = 0;
			var searchIndex : uint = 0;
			var l : uint = format.length;
			var y : int = 0;
			var mo : int = 0;
			var d : int = 1;
			var h : int = 0;
			var mi : int = 0;
			var s : int = 0;
			var ms : int = 0;
			var o : int = 0;

			while( i < l )
			{
				var char : String = format.charAt( i );
				var pattern : String = char;
				var str : String;
				var index : int;

				while( format.charAt( ++i ) == char && i < l ) pattern += char;

				switch( pattern )
				{
					case "yy" :
						str = from.substr( searchIndex, 2 );
						if( !__testInteger( str ) || str.length != 2 ) return null;
						y = int( String( new Date().getFullYear() ).substr( 0, 2 ) + str );
						searchIndex += 2;
						break;
					case "yyyy" :
						str = from.substr( searchIndex, 4 );
						if( !__testInteger( str ) || str.length != 4 ) return null;
						y = int( str );
						searchIndex += 4;
						break;
					case "MM"    :
						var so : String = from.substr( searchIndex, 2 );
						mo = int( so ) - 1;
						if( !__testInteger( so ) || ( mo < 0 || ( mo > 11 && !autoAddOverflow ) ) || so.length != 2 ) return null;
						searchIndex += 2;
						break;
					case "MMM" :
						mo = __searchArrayIndex( from.substr( searchIndex, 3 ), MONTHS_SHORT, caseSensitive );
						if( mo < 0 || ( mo > 11 && !autoAddOverflow ) ) return null;
						searchIndex += 3;
						break;
					case "MMMM" :
						mo = __searchArrayIndex( from.substr( searchIndex, 3 ), MONTHS, caseSensitive );
						if( mo < 0 || ( mo > 11 && !autoAddOverflow ) ) return null;
						searchIndex += String( MONTHS[ mo ] ).length;
						break;
					case "dd" :
						str = from.substr( searchIndex, 2 );
						d = int( str );
						if( !__testInteger( str ) || str.length != 2 || d <= 0 ) return null;
						searchIndex += 2;
						break;
					case "E" :
						index = __searchArrayIndex( from.substr( searchIndex, 3 ), WEEKDAY_SHORT, caseSensitive );
						if( index < 0 ) return null;
						str = String( WEEKDAY_SHORT[ index ] );
						searchIndex += str.length;
						break;
					case "EE" :
						index = __searchArrayIndex( from.substr( searchIndex, 3 ), WEEKDAY, caseSensitive );
						if( index < 0 ) return null;
						str = String( WEEKDAY[ index ] );
						searchIndex += str.length;
						break;
					case "hh" :
						str = from.substr( searchIndex, 2 );
						h = int( str );
						if( ( h < 0 || ( h > 12 && !autoAddOverflow ) ) || str.length != 2 ) return null;
						searchIndex += 2;
						break;
					case "HH" :
						str = from.substr( searchIndex, 2 );
						h = int( str );
						if( ( h < 0 || ( h > 23 && !autoAddOverflow ) ) || str.length != 2 ) return null;
						searchIndex += 2;
						break;
					case "mm" :
						str = from.substr( searchIndex, 2 );
						mi = int( str );
						if( ( mi < 0 || ( mi > 59 && !autoAddOverflow ) ) || str.length != 2 ) return null;
						searchIndex += 2;
						break;
					case "ss" :
						str = from.substr( searchIndex, 2 );
						s = int( str );
						if( ( s < 0 || ( s > 59 && !autoAddOverflow ) ) || str.length != 2 ) return null;
						searchIndex += 2;
						break;
					case "SS" :
						str = from.substr( searchIndex, 3 );
						ms = int( str );
						if( ( ms < 0 || ( ms > 999 && !autoAddOverflow ) ) || str.length != 3 ) return null;
						searchIndex += 3;
						break;
					case "o" :
						str = from.substr( searchIndex, 5 );
						if( !__testTimezoneOffset( str ) || String( str ).length != 5 ) return null;
						o = __timezoneOffset2Number( str ) * 60000;
						searchIndex += 5;
						break;
					case "O" :
						str = from.substr( searchIndex, 6 );
						if( !__testTimezoneOffset( str, true ) || String( str ).length != 6 ) return null;
						o = __timezoneOffset2Number( str, true ) * 60000;
						searchIndex += 6;
						break;
					default    :
						if( caseSensitive )
						{
							if( from.substr( searchIndex, pattern.length ) != pattern ) return null;
						}
						else
						{
							if( from.substr( searchIndex, pattern.length ).toLowerCase() != pattern.toLowerCase() ) return null;
						}
						searchIndex += pattern.length;
						break;
				}
			}

			// check month length if overflow is disabled
			if( !autoAddOverflow )
			{
				if( mo == 1 )
				{
					if( ( ( y % 4 == 0 ) && ( y % 100 != 0 ) ) || ( y % 400 == 0 ) )
					{
						if( d > 29 ) return null;
					}
					else if( d > 28 ) return null;
				}
				else if( mo == 3 || mo == 5 || mo == 8 || mo == 10 )
				{
					if( d > 30 ) return null;
				}
				else if( d > 31 ) return null;
			}
			// check there is no more char from source
			if( from.length > searchIndex ) return null;

			return new Date( new Date( y, mo, d, h, mi, s, ms ).getTime() + o );
		}

		/**
		 *
		 */
		private static function __convertPattern( pattern : String, d : Date ) : String
		{
			switch( true )
			{
				case pattern == "yy"    :
					return String( d.getFullYear() ).substr( 2 );
				case pattern == "yyyy"    :
					return String( d.getFullYear() );
				case pattern == "M"        :
					return String( d.getMonth() + 1 );
				case pattern == "MM"    :
					return digitFormat( d.getMonth() + 1 );
				case pattern == "MMM"    :
					return MONTHS_SHORT[ d.getMonth() ];
				case pattern == "MMMM"    :
					return MONTHS[ d.getMonth() ];
				case pattern == "d"        :
					return String( d.getDate() );
				case pattern == "dd"    :
					return digitFormat( d.getDate() );
				case pattern == "D"        :
					return String( __getDayYearNumber( d ) );
				case pattern == "DD"    :
					return digitFormat( __getDayYearNumber( d ), 3 );
				case pattern == "E"        :
					return WEEKDAY_SHORT[ d.getDay() ];
				case pattern == "EE"    :
					return WEEKDAY[ d.getDay() ];
				case pattern == "w"        :
					return String( __getWeekYearNumber( d ) );
				case pattern == "ww"    :
					return digitFormat( __getWeekYearNumber( d ) );
				case pattern == "h"        :
					return String( __hour24to12( d.getHours() ) );
				case pattern == "hh"    :
					return digitFormat( __hour24to12( d.getHours() ) );
				case pattern == "H"        :
					return String( d.getHours() );
				case pattern == "HH"    :
					return digitFormat( d.getHours() );
				case pattern == "m"        :
					return String( d.getMinutes() );
				case pattern == "mm"    :
					return digitFormat( d.getMinutes() );
				case pattern == "s"        :
					return String( d.getSeconds() );
				case pattern == "ss"    :
					return digitFormat( d.getSeconds() );
				case pattern == "S"        :
					return String( d.getMilliseconds() );
				case pattern == "SS"    :
					return digitFormat( d.getMilliseconds(), 3 );
				case pattern == "aa"    :
					return d.getHours() < 12 ? "am" : "pm";
				case pattern == "AA"    :
					return d.getHours() < 12 ? "AM" : "PM";
				case pattern == "o"        :
					return __timezoneOffset2String( d.getTimezoneOffset() );
				case pattern == "O"        :
					return __timezoneOffset2String( d.getTimezoneOffset(), ":" );
				default                    :
					var l : uint = pattern.length;
					return l > 1 ? __convertPattern( pattern.substr( 0, l - 1 ), d ) + pattern.substr( l - 1 ) : pattern;
			}
		}

		/**
		 *
		 */
		private static function __searchArrayIndex( s : String, a : Array, caseSensitive : Boolean = false ) : int
		{
			s = caseSensitive ? s : s.toLowerCase();

			var l : uint = a.length;

			for( var i : uint = 0; i < l; i++ )
			{
				var s1 : String = caseSensitive ? a[ i ] as String : ( a[ i ] as String ).toLowerCase();

				if( s1 == s ) return i;
			}

			return -1;
		}

		/**
		 *
		 */
		private static function __testInteger( s : String ) : Boolean
		{
			return isInteger( Number( s ) );
		}

		/**
		 *
		 */
		private static function __hour24to12( h : int ) : int
		{
			return h > 12 ? h - 12 : h;
		}

		/**
		 *
		 */
		private static function __getDayYearNumber( d : Date ) : uint
		{
			var monthsLength : Array = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
			var y : Number = d.fullYear;
			var dayNumber : uint;

			if( ( ( y % 4 == 0 ) && ( y % 100 != 0 ) ) || ( y % 400 == 0 ) ) monthsLength[ 1 ] = 29;

			var i : int = -1;
			var l : int = d.month;

			while( ++i < l ) dayNumber += monthsLength[ i ];

			return dayNumber + d.date;
		}

		/**
		 *
		 */
		private static function __getWeekYearNumber( d : Date ) : uint
		{
			var dayNumber : int = __getDayYearNumber( d );
			var firstYearDay : Date = new Date( d.fullYear, 0, 1 );
			var firstDay : Number = firstYearDay.day;

			firstDay = firstDay == 0 ? 7 : firstDay;
			dayNumber -= 8 - firstDay;

			var weekNumber : int = firstDay <= 4 ? 1 : 0;

			weekNumber += Math.floor( dayNumber / 7 );

			if( dayNumber % 7 != 0 ) weekNumber++;

			return weekNumber == 0 ? __getWeekYearNumber( new Date( d.fullYear - 1, 11, 31 ) ) : weekNumber;
		}

		/**
		 *
		 */
		private static function __timezoneOffset2String( n : int, separator : String = "" ) : String
		{
			var sign : String = n < 0 ? "+" : "-";
			var h : int = Math.abs( n ) / 60;
			var min : String = digitFormat( ( h - Math.floor( h ) ) * 60 );
			var hour : String = digitFormat( Math.floor( Math.abs( n ) / 60 ) );

			return sign + hour + separator + min;
		}

		/**
		 *
		 */
		private static function __timezoneOffset2Number( s : String, separator : Boolean = false ) : int
		{
			var n : int = int( s.substr( 1, 2 ) ) * 60 + int( s.substr( separator ? 4 : 3, 2 ) );

			return s.substr( 0, 1 ) == "+" ? -n : n;
		}

		/**
		 *
		 */
		private static function __testTimezoneOffset( s : String, separator : Boolean = false ) : Boolean
		{
			var a : Array = s.split( "" );

			return ( a[ 0 ] == "+" || a[ 0 ] == "-" ) && !isNaN( a[ 1 ] ) && !isNaN( a[ 2 ] ) && !isNaN( a[ separator ? 4 : 3 ] ) && !isNaN( a[ separator ? 5 : 4 ] );
		}

		/**
		 * @private
		 */
		public function DateFormatter()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
