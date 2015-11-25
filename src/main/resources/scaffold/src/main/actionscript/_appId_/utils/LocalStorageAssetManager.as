package _appId_.utils
{
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.net.URLRequest;
	import flash.utils.ByteArray;

	import starling.utils.AssetManager;

	public class LocalStorageAssetManager extends AssetManager
	{
		/**
		 * @private
		 */
		protected var _localFolder : File;

		/**
		 * @private
		 */
		protected var _activateReadLocalStorage : Boolean;

		/**
		 *
		 */
		public function get activateReadLocalStorage() : Boolean
		{
			return _activateReadLocalStorage;
		}

		public function set activateReadLocalStorage( b : Boolean ) : void
		{
			_activateReadLocalStorage = b;
		}

		/**
		 * @private
		 */
		protected var _activateWriteLocalStorage : Boolean;

		/**
		 *
		 */
		public function get activateWriteLocalStorage() : Boolean
		{
			return _activateWriteLocalStorage;
		}

		public function set activateWriteLocalStorage( b : Boolean ) : void
		{
			_activateWriteLocalStorage = b;
		}

		/**
		 *
		 */
		public function LocalStorageAssetManager( scaleFactor : Number = 1 , localFolder : File = null , activateReadLocalStorage : Boolean = false , activateWriteLocalStorage : Boolean = false )
		{
			super( scaleFactor , false );

			_activateReadLocalStorage = activateReadLocalStorage;
			_activateWriteLocalStorage = activateWriteLocalStorage;
			_localFolder = localFolder != null ? localFolder.resolvePath( "${projectName}" ) : File.cacheDirectory.resolvePath( "${projectName}" );
		}

		/**
		 *
		 */
		public function clearLocalStorage() : void
		{
			_localFolder.deleteDirectory( true );
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
		protected override function transformData( data : ByteArray , url : String ) : ByteArray
		{
			if( _activateWriteLocalStorage )
			{
				var fileStream : FileStream;
				var name : String = getName( url );
				var extension : String = getExtensionFromUrl( url );

				fileStream = new FileStream();
				fileStream.open( _localFolder.resolvePath( name + "." + extension ) , FileMode.WRITE );
				fileStream.writeBytes( data , 0 , data.length );
				fileStream.close();
			}

			return data;
		}

		/**
		 * @inheritDoc
		 */
		protected override function loadRawAsset( rawAsset : Object , onProgress : Function , onComplete : Function ) : void
		{
			if( _activateReadLocalStorage && ( rawAsset is String || rawAsset is URLRequest ) )
			{
				var url : String = rawAsset is String ? rawAsset as String : ( rawAsset as URLRequest ).url;
				var name : String = getName( url );
				var extension : String = getExtensionFromUrl( url );
				var f : File = _localFolder.resolvePath( extension != null ? name + "." + extension : name );

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
