package _appId_.view.core
{
	import com.flair.resources.ResourceFile;
	import com.flair.resources.ResourceFileManager;
	import com.flair.resources.addAssetManager;
	import com.flair.resources.removeAssetManager;
	import com.flair.utils.displayMetrics.getDensityScale;

	import feathers.core.IFeathersControl;
	import feathers.events.FeathersEventType;

	import flash.system.Capabilities;

	import org.osflash.signals.Signal;

	import starling.events.Event;
	import starling.textures.TextureOptions;
	import starling.utils.AssetManager;

	/**
	 *
	 */
	public class AAssetPanelScreen extends APanelScreen implements IAssetScreen
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
		public function AAssetPanelScreen()
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

			var instance : IFeathersControl = this;

			_initializeAssets( function ( ratio : Number ) : void
			{
				_assetProgress.dispatch( ratio );

				if( ratio == 1 )
				{
					initialize();

					_invalidationFlags = {};
					_isAllInvalid = false;
					_isInitializing = false;
					_isInitialized = true;

					invalidate();

					dispatchEventWith( FeathersEventType.INITIALIZE );

					if( _styleProvider ) _styleProvider.applyStyles( instance );

					_styleNameList.addEventListener( Event.CHANGE , styleNameList_changeHandler );
				}
			} );
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
				_assets.enqueueWithName( file.getFile() , null , new TextureOptions( file.drawableScale * getDensityScale() ) );
			}
		}
	}
}
