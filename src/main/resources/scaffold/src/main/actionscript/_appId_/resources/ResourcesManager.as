package _appId_.resources
{
	import _appId_.actors.STARLING;
	import _appId_.utils.*;
	import _appId_.utils.displayMetrics.EnumDensityBucket;
	import _appId_.utils.displayMetrics.densityBucket;
	import _appId_.view.EnumScreen;

	import flash.display3D.Context3DProfile;
	import flash.filesystem.File;

	/**
	 * @author SamYStudiO (contact@samystudio.net) on 29/11/2015.
	 */
	public final class ResourcesManager
	{
		/**
		 *
		 */
		private static var __instance : ResourcesManager;

		/**
		 *
		 */
		public static function getInstance() : ResourcesManager
		{
			if( __instance == null ) __instance = new ResourcesManager( new Singleton() );

			return __instance;
		}

		/**
		 * Regexps to retrieve resource qualifiers.
		 * Actually only language,region and dpi densityBucket are supported
		 */
		private const __REG_QUALIFIERS : Array = [ /((?:-)[a-z]{2,3}(?:[-|\/]))/ , /((?:-)r[A-Z]{2}(?:[-|\/]))/ , /(?:-)(ldpi|mdpi|hdpi|xhdpi|xxhdpi|xxxhdpi)/ ];

		/**
		 *
		 */
		private const __CURRENT_QUALIFIERS : Array = [ locale.getLanguage() , "r" + locale.getRegion() , densityBucket ];

		/**
		 * @private
		 */
		public function ResourcesManager( singleton : Singleton )
		{
			if( singleton == null ) throw new Error( this + " Singleton instance can only be accessed through getInstance method" );
		}

		/**
		 *
		 */
		public function getDrawables( screen : String = EnumScreen.MAIN ) : Vector.<File>
		{
			return getResource( EnumResource.DRAWABLE , screen );
		}

		/**
		 *
		 */
		public function getValues( screen : String = EnumScreen.MAIN ) : Vector.<File>
		{
			return getResource( EnumResource.VALUES , screen );
		}

		/**
		 *
		 */
		public function getXML( screen : String = EnumScreen.MAIN ) : Vector.<File>
		{
			return getResource( EnumResource.XML , screen );
		}

		/**
		 *
		 */
		public function getResource( id : String , screen : String = EnumScreen.MAIN ) : Vector.<File>
		{
			screen = screen == null || screen == EnumScreen.MAIN ? "" : screen;

			var resourceList : Array = File.applicationDirectory.resolvePath( "resources" ).getDirectoryListing();
			var a : Array = [];
			var reg : RegExp;
			var directory : File;
			var test : Array;
			var l : uint;
			var i : int;
			var match : String;

			files : for each ( directory in resourceList )
			{
				if( directory.isDirectory && directory.name.toLowerCase().indexOf( id ) == 0 )
				{
					l = __REG_QUALIFIERS.length;

					for( i = 0; i < l; i++ )
					{
						if( i == 2 ) continue;

						reg = __REG_QUALIFIERS[ i ];

						test = ( directory.name + "/" ).match( reg );

						if( test != null && test.length > 0 )
						{
							match = test[ 0 ];

							if( match.charAt( 0 ) == "-" ) match = match.substr( 1 );
							if( match.charAt( match.length - 1 ) == "-" || match.charAt( match.length - 1 ) == "/" ) match = match.substr( 0 , match.length - 1 );

							if( match != __CURRENT_QUALIFIERS[ i ] ) continue files;
						}
					}

					a.push( directory );
				}
			}

			l = __REG_QUALIFIERS.length;

			for( i = 0; i < l; i++ )
			{
				var b : Array = a.concat();

				reg = __REG_QUALIFIERS[ i ];

				if( i == 2 )
				{
					var d : int = int.MIN_VALUE;
					var buckets : Array = [ EnumDensityBucket.LDPI , EnumDensityBucket.MDPI , EnumDensityBucket.HDPI , EnumDensityBucket.XHDPI , EnumDensityBucket.XXHDPI , EnumDensityBucket.XXXHDPI ];
					var index : uint = buckets.indexOf( densityBucket );

					for each ( directory in a )
					{
						test = ( directory.name + "/" ).match( reg );

						if( test != null && test.length > 0 )
						{
							match = test[ 0 ];

							if( match.charAt( 0 ) == "-" ) match = match.substr( 1 );
							if( match.charAt( match.length - 1 ) == "-" || match.charAt( match.length - 1 ) == "/" ) match = match.substr( 0 , match.length - 1 );
							var matchIndex : int = buckets.indexOf( match );
							var diff : int = matchIndex - index;

							d = d < 0 && diff > 0 ? diff : diff < 0 && d > 0 ? d : d < 0 && diff < 0 ? Math.max( diff , d ) : d > 0 && diff > 0 ? Math.min( diff , d ) : 0;

							if( d == 0 ) break;
						}
					}

					var bucket : String = buckets[ index + d ];

					for each ( directory in a )
					{
						if( directory.name.indexOf( "-" + bucket ) < 0 && directory.name.indexOf( "nodpi" ) < 0 && ( directory.name + "/" ).match( reg ) ) b.removeAt( b.indexOf( directory ) );
					}
				}
				else
				{
					for each ( directory in a )
					{
						test = ( directory.name + "/" ).match( reg );

						if( test != null && test.length > 0 )
						{
							for each ( directory in a )
							{
								test = ( directory.name + "/" ).match( reg );
								if( ( test == null || test.length == 0 ) && directory.name.indexOf( "nodpi" ) < 0 )
								{
									b.removeAt( b.indexOf( directory ) );
								}
							}

							break;
						}
					}
				}

				a = b.concat();
			}

			var v : Vector.<File> = new Vector.<File>();

			if( a.length == 0 ) return v;

			for each ( directory in a )
			{
				directory = screen == "" ? directory : directory.resolvePath( screen );

				if( !directory.exists ) continue;

				var list : Array = directory.getDirectoryListing();

				for each ( var file : File in list )
				{
					if( !file.isDirectory )
					{
						var ext : String = file.name.toLowerCase().split( "." )[ 1 ];
						var atf : File;
						var png : File;

						if( ext == "atf" )
						{
							if( STARLING.profile == Context3DProfile.STANDARD_EXTENDED ) v.push( file )
						}
						else if( ext == "png" )
						{
							atf = file.parent.resolvePath( file.name.split( "." )[ 0 ] + ".atf" );

							if( STARLING.profile != Context3DProfile.STANDARD_EXTENDED || !atf.exists ) v.push( file )
						}
						else
						{
							v.push( file );
						}
					}
				}
			}

			return v;
		}

		/**
		 *
		 */
		public function getResources( screen : String = null ) : Vector.<File>
		{
			return getDrawables( screen ).concat( getValues( screen ) ).concat( getXML( screen ) )
		}

		/**
		 *
		 */
		public function getDrawableBucket() : String
		{
			var v : Vector.<File> = getDrawables();

			if( v.length > 0 )
			{
				var drawableDirectoryName : String = v[ 0 ].parent.name;

				var test : Array = drawableDirectoryName.match( __REG_QUALIFIERS[ 2 ] );

				if( test != null && test.length > 0 )
				{
					var match : String = test[ 0 ];

					if( match.charAt( 0 ) == "-" ) match = match.substr( 1 );
					if( match.charAt( match.length - 1 ) == "-" || match.charAt( match.length - 1 ) == "/" ) match = match.substr( 0 , match.length - 1 );

					return match;
				}
			}

			return EnumDensityBucket.MDPI;
		}
	}
}

internal class Singleton
{
}

