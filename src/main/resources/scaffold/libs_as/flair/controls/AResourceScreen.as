package flair.controls
{
	import flair.resources.ResourceFile;
	import flair.resources.ResourceFileManager;
	import flair.resources.addAssetManager;
	import flair.resources.removeAssetManager;
	import flair.utils.displayMetrics.getDensityScale;

	import feathers.controls.Screen;
	import feathers.core.IFeathersControl;
	import feathers.events.FeathersEventType;

	import flash.system.Capabilities;

	import org.osflash.signals.Signal;

	import starling.events.Event;
	import starling.textures.TextureOptions;
	import starling.utils.AssetManager;

	/**
	 * Feathers Screen with resource management
	 */
	public class AResourceScreen extends Screen implements IResourceScreen
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
		public function AResourceScreen()
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
		 * Override if you want to use custom AssetManager
		 */
		protected function _getAssetManager() : AssetManager
		{
			return new AssetManager( getDensityScale() );
		}

		/**
		 * In most case this screen will automatically load the right assets for you but if your really need to add custom assets
		 * you may override this
		 */
		protected function _addAssets() : void
		{
			var resourceList : Vector.<ResourceFile> = ResourceFileManager.getInstance().getResources( _screenID );

			for each ( var file : ResourceFile in resourceList )
			{
				_assets.enqueueWithName( file.getFile() , null , new TextureOptions( file.drawableScale * getDensityScale() ) );
			}
		}

		/**
		 *
		 */
		private function _initializeAssets( onProgress : Function ) : void
		{
			if( _assets ) return;

			_assets = _getAssetManager();
			_assets.verbose = Capabilities.isDebugger;

			addAssetManager( _assets , _screenID );

			_addAssets();

			_assets.loadQueue( onProgress );
		}
	}
}
