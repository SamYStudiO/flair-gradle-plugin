package flair.resources
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;

	import starling.utils.AssetManager;

	/**
	 * Add ability to AssetManager to cache external assets loaded through.
	 */
	public class CacheAssetManager extends AssetManager
	{
		/**
		 * File (directory) location where assets are stored.
		 */
		protected var _cacheDirectory : File;

		/**
		 * Enable/disable cache ability.
		 */
		public var cacheEnabled : Boolean = true;

		/**
		 * Indicates if assets just loaded and cached are also processed into memory.
		 */
		public var processCachedAsset : Boolean = false;

		/**
		 * Constructor.
		 * @param cacheDirectory File (directory) location where assets are stored.
		 *
		 * @see starling.utils.AssetManager
		 */
		public function CacheAssetManager( scaleFactor : Number = 1 , useMipmaps : Boolean = false , cacheDirectory : File = File.cacheDirectory.resolvePath( "assets" ) )
		{
			super( scaleFactor , useMipmaps );

			_cacheDirectory = cacheDirectory;
		}

		/**
		 * Clear all assets stored in cache directory.
		 */
		public function clearCacheDirectory() : void
		{
			_cacheDirectory.deleteDirectory( true );
		}

		/**
		 * @inheritDoc
		 */
		override protected function transformData( data : ByteArray , url : String ) : ByteArray
		{
			if( cacheEnabled && url.indexOf( "http" ) == 0 )
			{
				var fileStream : FileStream;
				var name : String = getName( url );
				var extension : String = getExtensionFromUrl( url );

				fileStream = new FileStream();
				fileStream.open( _cacheDirectory.resolvePath( extension != null ? name + "." + extension : name ) , FileMode.WRITE );
				fileStream.writeBytes( data , 0 , data.length );
				fileStream.close();

				if( !processCachedAsset ) return null;
			}
			
			return data;
		}

		/**
		 * @inheritDoc
		 */
		override protected function loadRawAsset( rawAsset : Object , onProgress : Function , onComplete : Function ) : void
		{
			if( cacheEnabled && ( rawAsset is String || rawAsset is URLRequest ) )
			{
				var url : String = rawAsset is String ? rawAsset as String : ( rawAsset as URLRequest ).url;
				var name : String = getName( url );
				var extension : String = getExtensionFromUrl( url );
				var f : File = _cacheDirectory.resolvePath( extension != null ? name + "." + extension : name );

				if( f.exists && url.indexOf( "http" ) == 0 )
				{
					if( rawAsset is String ) rawAsset = f.url;
					else ( rawAsset as URLRequest ).url = f.url;
				}
			}

			super.loadRawAsset( rawAsset , onProgress , onComplete );
		}
	}
}
