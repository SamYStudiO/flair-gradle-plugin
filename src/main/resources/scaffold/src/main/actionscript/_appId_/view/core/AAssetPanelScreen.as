package _appId_.view.core
{
	import _appId_.actors.RESOURCE_MANAGER;
	import _appId_.actors.STAGE;
	import _appId_.resources.ResourceFile;
	import _appId_.resources.addAssetManager;
	import _appId_.resources.removeAssetManager;
	import _appId_.utils.displayMetrics.getDensityScale;

	import feathers.system.DeviceCapabilities;

	import flash.system.Capabilities;

	import org.osflash.signals.Signal;

	import starling.textures.TextureOptions;
	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AAssetPanelScreen extends AScrollScreen implements IndexAssetScreen
	{
		/**
		 *
		 */
		protected var _assets : AssetManager;

		/**
		 * @private
		 */
		protected var _isReady : Boolean;

		/**
		 * @inheritDoc
		 */
		public function get isReady() : Boolean
		{
			return _isReady;
		}

		/**
		 * @private
		 */
		protected var _assetsProgress : Signal = new Signal( Number );

		/**
		 * @inheritDoc
		 */
		public function get assetsProgress() : Signal
		{
			return _assetsProgress;
		}

		/**
		 * @private
		 */
		protected var _assetsComplete : Signal = new Signal();

		/**
		 * @inheritDoc
		 */
		public function get assetsComplete() : Signal
		{
			return _assetsComplete;
		}

		/**
		 * @private
		 */
		protected var _assetsError : Signal = new Signal();

		/**
		 * @inheritDoc
		 */
		public function get assetsError() : Signal
		{
			return _assetsError;
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
			delete removeAssetManager( _screenID );
		}

		/**
		 * @inheritDoc
		 */
		override protected function initialize() : void
		{
			_initAssets();
		}

		/**
		 * @inheritDoc
		 */
		override protected function draw() : void
		{
			if( _isReady ) super.draw();
		}

		/**
		 *
		 */
		protected function _initAssets() : void
		{
			_assets = _getAssetManager();
			_assets.verbose = Capabilities.isDebugger;

			addAssetManager( _assets , _screenID );

			_addAssets();

			_assets.loadQueue( _onAssetsProgress );
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
			var resourcesList : Vector.<ResourceFile> = RESOURCE_MANAGER.getResources();

			for each ( var file : ResourceFile in resourcesList )
			{
				_assets.enqueueWithName( file.getFile() , null , new TextureOptions( file.drawableScale ) );
			}
		}

		/**
		 *
		 */
		protected function _onAssetsProgress( ratio : Number ) : void
		{
			_assetsProgress.dispatch( ratio );

			if( ratio == 1 ) _onAssetsComplete();
		}

		/**
		 *
		 */
		protected function _onAssetsComplete() : void
		{
			_initialize();

			if( DeviceCapabilities.isPhone( STAGE ) ) _initializePhone();
			else _initializeTablet();

			_isReady = true;
			_assetsComplete.dispatch();

			invalidate();
		}

		/**
		 *
		 */
		protected function _onAssetsError() : void
		{
			_assetsError.dispatch();
		}
	}
}
