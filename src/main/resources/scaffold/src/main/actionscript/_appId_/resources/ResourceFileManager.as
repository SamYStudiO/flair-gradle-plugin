package _appId_.resources
{
	import _appId_.utils.device.locale;
	import _appId_.utils.displayMetrics.EnumDensityDpi;
	import _appId_.utils.displayMetrics.densityDpi;
	import _appId_.utils.displayMetrics.getDensityScale;
	import _appId_.view.EnumScreen;

	import flash.display3D.Context3DProfile;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.system.Capabilities;
	import flash.utils.Dictionary;

	import starling.core.Starling;

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
			if( !__instance ) __instance = new ResourceFileManager( new Singleton() );

			return __instance;
		}

		/**
		 *
		 */
		private const __ENVIRONMENT_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.LOCALE_LANGUAGE , /-(dev|preprod)/ , CONFIG::ENVIRONMENT );

		/**
		 *
		 */
		private const __LOCALE_LANGUAGE_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.LOCALE_LANGUAGE , /-([a-z]{2})(?:-|$)/ , locale.getLanguage() );

		/**
		 *
		 */
		private const __LOCALE_REGION_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.LOCALE_REGION , /-(r[A-Z]{2})(?:-|$)/ , locale.getRegion() );

		/**
		 *
		 */
		private const __SMALLEST_WIDTH_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.SMALLEST_WIDTH , /(sw[0-9]{2,4}dp)/ , Math.min( Starling.current.stage.stageWidth , Starling.current.stage.stageHeight ) , function ( qualifierValue : String , testValue : String ) : Boolean
		{
			function parseSWInt( s : String ) : int
			{
				return int( s.replace( "sw" , "" ).replace( "dp" , "" ) )
			}

			return parseSWInt( testValue ) >= parseSWInt( qualifierValue );
		} );

		/**
		 *
		 */
		private const __DENSITY_QUALIFIER : Qualifier = new Qualifier( EnumQualifier.DENSITY , /-(ldpi|mdpi|hdpi|xhdpi|xxhdpi|xxxhdpi)/ , _getBucketFromDensityDpi() );

		/**
		 *
		 */
		private const __QUALIFIERS : Array = [ __ENVIRONMENT_QUALIFIER , __LOCALE_LANGUAGE_QUALIFIER , __LOCALE_REGION_QUALIFIER , __SMALLEST_WIDTH_QUALIFIER , __DENSITY_QUALIFIER ];

		/**
		 * @private
		 */
		public function ResourceFileManager( singleton : Singleton )
		{
			if( !singleton ) throw new Error( this + " Singleton instance can only be accessed through getInstance method" );
		}

		/**
		 *
		 */
		public function getDrawables( screenID : String = EnumScreen.MAIN ) : Vector.<ResourceFile>
		{
			return getResource( EnumResourceType.DRAWABLE , screenID );
		}

		/**
		 *
		 */
		public function getValues( screenID : String = EnumScreen.MAIN ) : Vector.<ResourceFile>
		{
			var outputFile : File = File.applicationStorageDirectory.resolvePath( "resources/" + screenID + "/values.xml" );

			if( !outputFile.exists || Capabilities.isDebugger )
			{
				var outputXML : XML = <root />;
				var stream : FileStream = new FileStream();
				var values : Vector.<ResourceFile> = getResource( EnumResourceType.VALUES , screenID );

				for each ( var file : ResourceFile in values )
				{
					stream.open( file , FileMode.READ );
					outputXML.appendChild( new XML( stream.readUTFBytes( stream.bytesAvailable ) ) );
					stream.close();
				}

				stream.open( outputFile , FileMode.WRITE );
				stream.writeUTFBytes( outputXML.toString() );
				stream.close();
			}

			var v : Vector.<ResourceFile> = new Vector.<ResourceFile>();
			v.push( ResourceFile.fromFile( outputFile , EnumResourceType.VALUES ) );

			return v;
		}

		/**
		 *
		 */
		public function getXML( screenID : String = EnumScreen.MAIN ) : Vector.<ResourceFile>
		{
			return getResource( EnumResourceType.XML , screenID );
		}

		/**
		 *
		 */
		public function getRaw( screenID : String = EnumScreen.MAIN ) : Vector.<ResourceFile>
		{
			return getResource( EnumResourceType.RAW , screenID );
		}

		/**
		 *
		 */
		public function getResource( resourceType : String , screenID : String = EnumScreen.MAIN ) : Vector.<ResourceFile>
		{
			var resourceList : Dictionary = _getResourceList( resourceType , screenID );
			var fileName : String;
			var v : Vector.<ResourceFile> = new Vector.<ResourceFile>();

			for( fileName in resourceList )
			{
				var resourceNameList : Vector.<File> = resourceList[ fileName ];
				var directoryList : Vector.<File> = new Vector.<File>;
				var qualifier : Qualifier;
				var test : Array;
				var match : String;
				var validDirectories : Vector.<File> = new Vector.<File>();
				var file : File;
				var directory : File;
				var sw : Array = [];

				for each ( file in resourceNameList )
				{
					directory = screenID == EnumScreen.MAIN ? file.parent : file.parent.parent;

					if( directoryList.indexOf( directory ) < 0 ) directoryList.push( directory );
				}

				directories : for each ( directory in directoryList )
				{
					for each ( qualifier in __QUALIFIERS )
					{
						if( qualifier.name == EnumQualifier.DENSITY ) continue;

						test = directory.name.match( qualifier.regexp );

						if( test != null && test.length > 0 )
						{
							match = test[ 0 ].replace( /-/g , "" );

							if( !qualifier.test( match ) ) continue directories;
							else if( qualifier.name == EnumQualifier.SMALLEST_WIDTH )
							{
								sw.push( {file : directory , sw : int( match.replace( "sw" , "" ).replace( "dp" , "" ) )} );
							}
						}
					}

					validDirectories.push( directory );
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
						if( o.sw != max ) validDirectories.removeAt( validDirectories.indexOf( o.file ) );
					}
				}

				for each ( qualifier in __QUALIFIERS )
				{
					var b : Vector.<File> = validDirectories.concat();

					if( qualifier.name == EnumQualifier.DENSITY )
					{
						var d : int = int.MIN_VALUE;
						var buckets : Array = [ "ldpi" , "mdpi" , "hdpi" , "xhdpi" , "xxhdpi" , "xxxhdpi" ];

						var index : uint = buckets.indexOf( _getBucketFromDensityDpi() );
						var has2x : Boolean = false;
						var hasNodpi : Boolean = false;

						for each ( directory in validDirectories )
						{
							if( directory.name.indexOf( "nodpi" ) > 0 )
							{
								hasNodpi = true;
								break;
							}

							test = directory.name.match( qualifier.regexp );

							if( test && test.length > 0 )
							{
								match = test[ 0 ].replace( /-/g , "" );

								var matchIndex : int = buckets.indexOf( match );
								var diff : int = matchIndex - index;

								d = d < 0 && diff > 0 ? diff : diff < 0 && d > 0 ? d : d < 0 && diff < 0 ? Math.max( diff , d ) : d > 0 && diff > 0 ? Math.min( diff , d ) : 0;

								if( diff == 2 ) has2x = true;

								if( d == 0 ) break;
							}
						}

						var bucket : String = hasNodpi ? "nodpi" : buckets[ index + ( d == 0 ? 0 : has2x ? 2 : d ) ];

						for each ( directory in validDirectories )
						{
							if( directory.name.indexOf( "-" + bucket ) < 0 && directory.name.match( qualifier.regexp ) ) b.removeAt( b.indexOf( directory ) );
						}
					}
					else
					{
						for each ( directory in validDirectories )
						{
							test = directory.name.match( qualifier.regexp );

							if( test && test.length > 0 )
							{
								for each ( directory in validDirectories )
								{
									test = directory.name.match( qualifier.regexp );

									if( ( !test || test.length == 0 ) )
									{
										b.removeAt( b.indexOf( directory ) );
									}
								}

								break;
							}
						}
					}

					validDirectories = b.concat();
				}

				directory = validDirectories.length ? validDirectories[ 0 ] : null;

				if( directory )
				{
					var list : Array = screenID == EnumScreen.MAIN ? directory.getDirectoryListing() : directory.resolvePath( screenID ).getDirectoryListing();

					for each ( file in list )
					{
						var name : String = file.name.split( "." )[ 0 ];

						if( !file.isDirectory && name == fileName )
						{
							var ext : String = file.extension;
							var atf : File;
							var resourceFile : ResourceFile = ResourceFile.fromFile( file , resourceType );
							var parentDirectory : File = screenID == EnumScreen.MAIN ? file.parent : file.parent.parent;
							var parentDirectoryName : String = parentDirectory.name;
							var scale : Number = 1.0;

							if( resourceType == EnumResourceType.DRAWABLE )
							{
								switch( true )
								{
									case parentDirectoryName == "drawable" || parentDirectoryName.indexOf( "-mdpi" ) > 0 :
										scale = EnumDensityDpi.DENSITY_MDPI / densityDpi * getDensityScale();
										break;
									case parentDirectoryName.indexOf( "-ldpi" ) > 0 :
										scale = EnumDensityDpi.DENSITY_LDPI / densityDpi * getDensityScale();
										break;
									case parentDirectoryName.indexOf( "-hdpi" ) > 0 :
										scale = EnumDensityDpi.DENSITY_HDPI / densityDpi * getDensityScale();
										break;
									case parentDirectoryName.indexOf( "-xhdpi" ) > 0 :
										scale = EnumDensityDpi.DENSITY_XHDPI / densityDpi * getDensityScale();
										break;
									case parentDirectoryName.indexOf( "-xxhdpi" ) > 0 :
										scale = EnumDensityDpi.DENSITY_XXHDPI / densityDpi * getDensityScale();
										break;
									case parentDirectoryName.indexOf( "-xxxhdpi" ) > 0 :
										scale = EnumDensityDpi.DENSITY_XXXHDPI / densityDpi * getDensityScale();
										break;

									default :
										scale = 1.0;
								}
							}

							resourceFile.drawableScale = scale;

							if( ext == "atf" )
							{
								if( Starling.current.profile == Context3DProfile.STANDARD_EXTENDED ) v.push( resourceFile )
							}
							else if( ext == "png" )
							{
								atf = file.parent.resolvePath( file.name.split( "." )[ 0 ] + ".atf" );

								if( Starling.current.profile != Context3DProfile.STANDARD_EXTENDED || !atf.exists ) v.push( resourceFile )
							}
							else
							{
								v.push( resourceFile );
							}
						}
					}
				}
			}

			return v;
		}

		/**
		 *
		 */
		public function getResources( screenID : String = EnumScreen.MAIN ) : Vector.<ResourceFile>
		{
			return getDrawables( screenID ).concat( getXML( screenID ) ).concat( getValues( screenID ) ).concat( getRaw( screenID ) )
		}

		/**
		 *
		 */
		private function _getBucketFromDensityDpi() : String
		{
			switch( true )
			{
				case densityDpi < 130 :
					return "ldpi";
				case densityDpi < 170 :
					return "mdpi";
				case densityDpi < 250 :
					return "hdpi";
				case densityDpi < 330 :
					return "xhdpi";
				case densityDpi < 490 :
					return "xxhdpi";
				default :
					return "xxxhdpi";
			}

		}

		/**
		 *
		 */
		private function _getResourceList( resourceType : String , screenID : String = EnumScreen.MAIN ) : Dictionary
		{
			var d : Dictionary = new Dictionary( true );
			var directoryList : Array = File.applicationDirectory.resolvePath( "resources" ).getDirectoryListing();

			for each ( var directory : File in directoryList )
			{
				if( directory.isDirectory && directory.name.toLowerCase().indexOf( resourceType ) == 0 )
				{
					var fileList : Array = screenID == EnumScreen.MAIN ? directory.getDirectoryListing() : directory.resolvePath( screenID ).getDirectoryListing();

					for each ( var file : File in fileList )
					{
						var filename : String = file.name.split( "." )[ 0 ];

						if( d[ filename ] == undefined ) d[ filename ] = new Vector.<File>();

						d[ filename ].push( file );
					}
				}
			}

			return d;
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
	public static const ENVIRONMENT : String = "environment";

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
		return matchFunction ? matchFunction( this.value , value ) : value === this.value;
	}
}


