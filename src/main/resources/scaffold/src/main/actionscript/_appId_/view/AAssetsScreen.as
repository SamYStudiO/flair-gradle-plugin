package _appId_.view
{
	import _appId_.actors.ASSET_MANAGER;
	import _appId_.actors.STAGE;
	import _appId_.theme.assetScale;

	import feathers.system.DeviceCapabilities;

	import org.osflash.signals.Signal;

	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AAssetsScreen extends AScreen implements IndexedAssetsScreen
	{
		/**
		 * @private
		 */
		protected var _assets : AssetManager;
		/**
		 * @private
		 */
		protected var _assetsProgressEvent : Signal = new Signal( Number );
		/**
		 * @private
		 */
		protected var _assetsCompleteEvent : Signal = new Signal();
		/**
		 * @private
		 */
		protected var _assetsErrorEvent : Signal = new Signal();

		/**
		 * @private
		 */
		protected var _isReady : Boolean;

		/**
		 *
		 */
		public function get isReady() : Boolean
		{
			return _isReady;
		}

		/**
		 *
		 */
		public function get assetsProgress() : Signal
		{
			return _assetsProgressEvent;
		}

		/**
		 *
		 */
		public function get assetsComplete() : Signal
		{
			return _assetsCompleteEvent;
		}

		/**
		 *
		 */
		public function get assetsError() : Signal
		{
			return _assetsErrorEvent;
		}

		/**
		 *
		 */
		public function AAssetsScreen()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		public override function dispose() : void
		{
			super.dispose();

			if( _assets != null ) _assets.purge();
			delete ASSET_MANAGER[ _screenID ];
		}

		/**
		 * @inheritDoc
		 */
		protected override function initialize() : void
		{
			_initAssets();
		}

		/**
		 * @inheritDoc
		 */
		protected override function draw() : void
		{
			if( _isReady ) super.draw();
		}

		/**
		 * @private
		 */
		protected function _initAssets() : void
		{
			_assets = _getAssetManager();
			_assets.verbose = Boolean( CONFIG::DEBUG );

			ASSET_MANAGER[ _screenID ] = _assets;

			_addAssets();

			_assets.loadQueue( _assetsProgress );
		}

		/**
		 * @private
		 */
		protected function _getAssetManager() : AssetManager
		{
			return new AssetManager( 1 / assetScale );
		}

		/**
		 * @private
		 */
		protected function _addAssets() : void
		{
		}

		/**
		 * @private
		 */
		protected function _assetsProgress( ratio : Number ) : void
		{
			_assetsProgressEvent.dispatch( ratio );

			if( ratio == 1 ) _assetsComplete();
		}

		/**
		 * @private
		 */
		protected function _assetsComplete() : void
		{
			_initialize();

			if( DeviceCapabilities.isPhone( STAGE ) ) _initializePhone();
			else _initializeTablet();

			_isReady = true;
			_assetsCompleteEvent.dispatch();

			invalidate();
		}

		/**
		 * @private
		 */
		protected function _assetsError() : void
		{
			_assetsErrorEvent.dispatch();
		}
	}
}
