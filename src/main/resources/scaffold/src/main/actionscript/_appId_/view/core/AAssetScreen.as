package _appId_.view.core
{
	import _appId_.resources.ResourceFile;
	import _appId_.resources.ResourceFileManager;
	import _appId_.resources.addAssetManager;
	import _appId_.resources.removeAssetManager;
	import _appId_.utils.displayMetrics.getDensityScale;

	import feathers.events.FeathersEventType;

	import flash.system.Capabilities;

	import org.osflash.signals.Signal;

	import starling.events.Event;
	import starling.textures.TextureOptions;
	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AAssetScreen extends AScreen implements IAssetScreen
	{
		/**
		 *
		 */
		protected var _assets : AssetManager;

		/**
		 * @private
		 */
		protected var _assetProgress : Signal = new Signal( Number );

		/**
		 * @inheritDoc
		 */
		public function get assetProgress() : Signal
		{
			return _assetProgress;
		}

		/**
		 *
		 */
		public function AAssetScreen()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		override public function dispose() : void
		{
			super.dispose();

			if( _assets != null ) _assets.purge();
			removeAssetManager( _screenID );
		}

		/**
		 * @inheritDoc
		 */
		override protected function initializeInternal() : void
		{
			if( _isInitialized || _isInitializing ) return;

			_isInitializing = true;

			_initializeAssets( function ( ratio : Number ) : void
			{
				_assetProgress.dispatch( ratio );

				if( ratio == 1 )
				{
					initialize();
					invalidate();
					_isInitializing = false;
					_isInitialized = true;
					dispatchEventWith( FeathersEventType.INITIALIZE );
				}
			} );

			if( _styleProvider ) _styleProvider.applyStyles( this );

			_styleNameList.addEventListener( Event.CHANGE , styleNameList_changeHandler );
		}

		/**
		 *
		 */
		protected function _initializeAssets( onProgress : Function ) : void
		{
			if( _assets ) return;

			_assets = _getAssetManager();
			_assets.verbose = Capabilities.isDebugger;

			addAssetManager( _assets , _screenID );

			_addAssets();

			_assets.loadQueue( onProgress );
		}

		/**
		 *
		 */
		protected function _getAssetManager() : AssetManager
		{
			return new AssetManager( getDensityScale() );
		}

		/**
		 *
		 */
		protected function _addAssets() : void
		{
			var resourceList : Vector.<ResourceFile> = ResourceFileManager.getInstance().getResources( _screenID );

			for each ( var file : ResourceFile in resourceList )
			{
				_assets.enqueueWithName( file.getFile() , null , new TextureOptions( file.drawableScale ) );
			}
		}
	}
}
