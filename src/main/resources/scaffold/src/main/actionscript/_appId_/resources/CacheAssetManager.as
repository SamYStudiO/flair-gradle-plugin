package _appId_.resources
{
	import _appId_.utils.string.isUrl;

	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;

	import myLogger.log;

	import starling.events.EventDispatcher;
	import starling.textures.TextureOptions;

	public class CacheAssetManager extends EventDispatcher
	{
		/**
		 *
		 */
		protected var _cacheDirectory : File;

		/**
		 *
		 */
		public function CacheAssetManager( scaleFactor : Number = 1 , cahceFolder : File = File.cacheDirectory.resolvePath( "${projectName}" ) )
		{
			super( scaleFactor , false );

			_cacheDirectory = localFolder != null ? localFolder.resolvePath( "${projectName}" ) : File.cacheDirectory.resolvePath( "${projectName}" );
		}

		/**
		 *
		 */
		public function cache( ...rawAssets ) : void
		{
			for each ( var rawAsset : Object in rawAssets )
			{
				if( rawAsset is Array )
				{
					cache.apply( this , rawAsset );
				}
				else if( rawAsset is String || rawAsset is URLRequest )
				{
					cacheWithName( rawAsset );
				}
				else
				{
					log( "Ignoring unsupported asset type: " + getQualifiedClassName( rawAsset ) );
				}
			}
		}

		/**
		 *
		 */
		public function cacheWithName( url : * , name : String = null , options : TextureOptions = null ) : void
		{
			if( !( url is URLRequest ) || !isUrl( url.toString() ) ) throw new ArgumentError( "url is either a String or a URLRequest object" )

		}

		/**
		 *
		 */
		public function clearLocalStorage() : void
		{
			_cacheDirectory.deleteDirectory( true );
		}

		/**
		 *
		 */
		public function getAssetName( asset : Object ) : String
		{
			return getName( asset );
		}

		/**
		 * @inheritDoc
		 */
		override protected function transformData( data : ByteArray , url : String ) : ByteArray
		{
			if( _activateWriteLocalStorage )
			{
				var fileStream : FileStream;
				var name : String = getName( url );
				var extension : String = getExtensionFromUrl( url );

				fileStream = new FileStream();
				fileStream.open( _cacheDirectory.resolvePath( name + "." + extension ) , FileMode.WRITE );
				fileStream.writeBytes( data , 0 , data.length );
				fileStream.close();
			}

			return data;
		}

		/**
		 * @inheritDoc
		 */
		override protected function loadRawAsset( rawAsset : Object , onProgress : Function , onComplete : Function ) : void
		{
			if( _activateReadLocalStorage && ( rawAsset is String || rawAsset is URLRequest ) )
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
