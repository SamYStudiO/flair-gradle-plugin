package _appId_.resources
{
	import _appId_.utils.string.isUrl;

	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;

	import starling.utils.AssetManager;

	public class CacheAssetManager extends AssetManager
	{
		/**
		 *
		 */
		protected var _cacheDirectory : File;

		/**
		 * Enable or disable read/write from cache directory.
		 * When enabled all external files (http files) are written into cache directory.
		 *
		 * @default true
		 */
		public var cacheEnabled : Boolean = true;

		/**
		 * Indicates if files that have just been written into cache are process into memory as well like any other asset.
		 *
		 * @default false
		 */
		public var processCachedAsset : Boolean = false;

		/**
		 *
		 */
		public function CacheAssetManager( scaleFactor : Number = 1 , useMipmaps : Boolean = false , cacheDirectory : File = File.cacheDirectory.resolvePath( "assets" ) )
		{
			super( scaleFactor , useMipmaps );

			_cacheDirectory = cacheDirectory;
		}

		/**
		 *
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
			if( cacheEnabled && isUrl( url ) )
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
			else return data;
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

				if( f.exists && isUrl( url ) )
				{
					if( rawAsset is String ) rawAsset = f.url;
					else ( rawAsset as URLRequest ).url = f.url;
				}
			}

			super.loadRawAsset( rawAsset , onProgress , onComplete );
		}
	}
}
