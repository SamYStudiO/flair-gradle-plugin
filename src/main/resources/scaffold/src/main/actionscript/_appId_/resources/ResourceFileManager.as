package _appId_.resources
{
	import _appId_.actors.STAGE;
	import _appId_.actors.STARLING;
	import _appId_.utils.device.deviceLocale;
	import _appId_.utils.displayMetrics.EnumDensityBucket;
	import _appId_.utils.displayMetrics.deviceBucket;
	import _appId_.utils.displayMetrics.getDensityScale;
	import _appId_.view.EnumScreen;

	import flash.display3D.Context3DProfile;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;

	/**
	 * @author SamYStudiO (contact@samystudio.net) on 29/11/2015.
	 */
	public final class ResourceFileManager
	{
		/**
		 *
		 */
		private static var __instance : ResourceFileManager;

		/**
		 *
		 */
		public static function getInstance() : ResourceFileManager
		{
			if( __instance == null ) __instance = new ResourceFileManager( new Singleton() );

			return __instance;
		}

		/**
		 *
		 */
		private const __LOCALE_LANGUAGE_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.LOCALE_LANGUAGE , /-([a-z]{2,3})(?:-|$)/ , deviceLocale.getLanguage() );

		/**
		 *
		 */
		private const __LOCALE_REGION_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.LOCALE_REGION , /-(r[A-Z]{2})(?:-|$)/ , deviceLocale.getRegion() );

		/**
		 *
		 */
		private const __SMALLEST_WIDTH_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.SMALLEST_WIDTH , /(sw[0-9]{2,4}dp)/ , Math.min( STAGE.stageWidth / getDensityScale() , STAGE.stageHeight / getDensityScale() ) , function ( qualifierValue : String , testValue : String ) : Boolean
		{
			function parseSWInt( s : String ) : int
			{
				return int( s.replace( "sw" , "" ).replace( "dp" , "" ) )
			}

			return parseSWInt( testValue ) >= parseSWInt( qualifierValue );
		} );

		/**
		 * TODO make 560 bucket a valid bucket (nexus 6)
		 */
		private const __DENSITY_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.DENSITY , /-(ldpi|mdpi|hdpi|xhdpi|xxhdpi|xxxhdpi)/ , deviceBucket );

		/**
		 *
		 */
		private const __QUALIFIERS : Array = [ __LOCALE_LANGUAGE_QUALIFIER , __LOCALE_REGION_QUALIFIER , __SMALLEST_WIDTH_QUALIFIER , __DENSITY_QUALIFIER ];

		/**
		 * @private
		 */
		public function ResourceFileManager( singleton : Singleton )
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
		public function getValues( screen : String = EnumScreen.MAIN ) : XML
		{
			var v : Vector.<File> = getResource( EnumResource.VALUES , screen );
			var output : XML = <root />;
			var stream : FileStream = new FileStream();

			for each ( var file : File in v )
			{
				stream.open( file , FileMode.READ );
				output.appendChild( new XML( stream.readUTFBytes( stream.bytesAvailable ) ) );
			}

			return output;
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
			var qualifier : Qualifier;
			var directory : File;
			var test : Array;
			var match : String;
			var sw : Array = [];

			files : for each ( directory in resourceList )
			{
				if( directory.isDirectory && directory.name.toLowerCase().indexOf( id ) == 0 )
				{

					for each ( qualifier in __QUALIFIERS )
					{
						if( qualifier.name == EnumQualifier.DENSITY ) continue;

						test = directory.name.match( qualifier.regexp );

						if( test != null && test.length > 0 )
						{
							match = test[ 0 ];

							if( !qualifier.test( match ) ) continue files;
							else if( qualifier.name == EnumQualifier.SMALLEST_WIDTH )
							{
								sw.push( {file : directory , sw : int( match.replace( "sw" , "" ).replace( "dp" , "" ) )} );
							}
						}
					}

					a.push( directory );
				}
			}

			if( sw.length > 1 )
			{
				var max : Number = 0;

				for each ( var o : Object in sw )
				{
					max = Math.max( max , o.sw );
				}

				for each ( o in sw )
				{
					if( o.sw != max ) a.removeAt( a.indexOf( o.file ) );
				}
			}

			for each ( qualifier in __QUALIFIERS )
			{
				var b : Array = a.concat();

				if( qualifier.name == EnumQualifier.DENSITY )
				{
					var d : int = int.MIN_VALUE;
					var buckets : Array = [ EnumDensityBucket.LDPI , EnumDensityBucket.MDPI , EnumDensityBucket.HDPI , EnumDensityBucket.XHDPI , EnumDensityBucket.XXHDPI , EnumDensityBucket.XXXHDPI ];
					var index : uint = buckets.indexOf( deviceBucket );
					var has2x : Boolean;

					for each ( directory in a )
					{
						test = directory.name.match( qualifier.regexp );

						if( test != null && test.length > 0 )
						{
							match = test[ 0 ];

							var matchIndex : int = buckets.indexOf( match );
							var diff : int = matchIndex - index;

							d = d < 0 && diff > 0 ? diff : diff < 0 && d > 0 ? d : d < 0 && diff < 0 ? Math.max( diff , d ) : d > 0 && diff > 0 ? Math.min( diff , d ) : 0;

							if( diff == 2 ) has2x = true;

							if( d == 0 ) break;
						}
					}

					var bucket : String = buckets[ index + ( d == 0 ? 0 : has2x ? 2 : d ) ];

					for each ( directory in a )
					{
						if( directory.name.indexOf( "-" + bucket ) < 0 && directory.name.indexOf( "nodpi" ) < 0 && directory.name.match( qualifier.regexp ) ) b.removeAt( b.indexOf( directory ) );
					}
				}
				else
				{
					for each ( directory in a )
					{
						test = directory.name.match( qualifier.regexp );

						if( test != null && test.length > 0 )
						{
							match = test[ 0 ];

							for each ( directory in a )
							{
								test = directory.name.match( qualifier.regexp );

								if( test == null || test.length == 0 )
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
			return getDrawables( screen ).concat( getXML( screen ) )
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

				var test : Array = drawableDirectoryName.match( __DENSITY_QUALIFIER.regexp );

				if( test != null && test.length > 0 )
				{
					return test[ 0 ];
				}
			}

			return EnumDensityBucket.MDPI;
		}
	}
}

class Singleton
{
}

class EnumQualifier
{
	/**
	 *
	 */
	public static const LOCALE_LANGUAGE : String = "localeLanguage";

	/**
	 *
	 */
	public static const LOCALE_REGION : String = "localeRegion";

	/**
	 *
	 */
	public static const SMALLEST_WIDTH : String = "smallestWidth";

	/**
	 *
	 */
	public static const DENSITY : String = "density";

	/**
	 * @private
	 */
	public function EnumQualifier() : void
	{
		throw new Error( this + " cannot be instantiated" );
	}
}

class Qualifier
{
	/**
	 *
	 */
	public var name : String;

	/**
	 *
	 */
	public var regexp : RegExp;

	/**
	 *
	 */
	public var value : Object;

	/**
	 *
	 */
	public var matchFunction : Function;

	/**
	 *
	 */
	public function Qualifier( name : String , regexp : RegExp , value : Object , matchFunction : Function = null )
	{
		this.name = name;
		this.regexp = regexp;
		this.value = value;
		this.matchFunction = matchFunction;
	}

	/**
	 *
	 */
	public function test( value : Object ) : Boolean
	{
		return matchFunction != null ? matchFunction( this.value , value ) : value === this.value;
	}
}


