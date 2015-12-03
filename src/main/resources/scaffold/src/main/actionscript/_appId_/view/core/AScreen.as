package _appId_.view.core
{
	import _appId_.actors.NAVIGATOR;
	import _appId_.actors.STAGE;

	import feathers.controls.Screen;
	import feathers.system.DeviceCapabilities;

	import org.osflash.signals.Signal;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AScreen extends Screen implements IndexScreen
	{
		/**
		 * @private
		 */
		protected var _index : uint;

		/**
		 * @inheritDoc
		 */
		public function get index() : uint
		{
			return _index;
		}

		public function set index( index : uint ) : void
		{
			_index = index;
		}

		/**
		 * @private
		 */
		protected var _params : Object = {};

		/**
		 * @inheritDoc
		 */
		public function get params() : Object
		{
			return _params;
		}

		/**
		 * @private
		 */
		protected var _shown : Signal = new Signal();

		/**
		 * @inheritDoc
		 */
		public function get shown() : Signal
		{
			return _shown;
		}

		/**
		 * @private
		 */
		protected var _hidden : Signal = new Signal();

		/**
		 * @inheritDoc
		 */
		public function get hidden() : Signal
		{
			return _hidden;
		}

		/**
		 *
		 */
		public function AScreen()
		{
			super();

			backButtonHandler = _goBack;
		}

		/**
		 * @inheritDoc
		 */
		public function show() : void
		{
		}

		/**
		 * @inheritDoc
		 */
		public function hide() : void
		{
		}

		/**
		 * @inheritDoc
		 */
		override protected function initialize() : void
		{
			super.initialize();

			_initialize();
			if( DeviceCapabilities.isPhone( STAGE ) ) _initializePhone();
			else _initializeTablet();
		}

		/**
		 * @inheritDoc
		 */
		override protected function draw() : void
		{
			super.draw();

			_draw();

			if( DeviceCapabilities.isPhone( STAGE ) ) _drawPhone();
			else _drawTablet();
		}

		/**
		 *
		 */
		protected function _initialize() : void
		{
		}

		/**
		 *
		 */
		protected function _initializePhone() : void
		{
		}

		/**
		 *
		 */
		protected function _initializeTablet() : void
		{
		}

		/**
		 *
		 */
		protected function _draw() : void
		{
		}

		/**
		 *
		 */
		protected function _drawPhone() : void
		{
		}

		/**
		 *
		 */
		protected function _drawTablet() : void
		{
		}

		/**
		 *
		 */
		protected function _goBack() : void
		{
			NAVIGATOR.showPreviousScreen();
		}
	}
}
