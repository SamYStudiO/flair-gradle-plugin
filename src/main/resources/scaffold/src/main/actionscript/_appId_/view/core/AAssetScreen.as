package _appId_.view.core
{
	import _appId_.actors.R;
	import _appId_.actors.STAGE;
	import _appId_.resources.addAssetManager;
	import _appId_.resources.removeAssetManager;
	import _appId_.utils.displayMetrics.getDensityScale;
	import _appId_.utils.displayMetrics.getDrawableScale;

	import feathers.system.DeviceCapabilities;

	import flash.filesystem.File;
	import flash.system.Capabilities;

	import org.osflash.signals.Signal;

	import starling.utils.AssetManager;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AAssetScreen extends AScreen implements IndexAssetScreen
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
			return new AssetManager( getDrawableScale() * getDensityScale() );
		}

		/**
		 *
		 */
		protected function _addAssets() : void
		{
			var resourcesList : Vector.<File> = R.getResources( _screenID );

			for each ( var file : File in resourcesList )
			{
				_assets.enqueue( file );
			}

			_assets.addXml( "values" , R.getValues( _screenID ) );
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
