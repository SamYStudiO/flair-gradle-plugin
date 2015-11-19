package _appId_.view
{
	import _appId_.actors.NAVIGATOR;
	import _appId_.actors.STAGE;

	import feathers.controls.Screen;
	import feathers.system.DeviceCapabilities;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AScreen extends Screen implements IndexedScreen
	{
		/**
		 * @private
		 */
		protected var _index : uint;

		/**
		 *
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
		 *
		 */
		public function get params() : Object
		{
			return _params;
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
		 *
		 */
		public function show() : void
		{
		}

		/**
		 *
		 */
		public function hide() : void
		{
		}

		/**
		 *
		 */
		public function shown() : void
		{
		}

		/**
		 *
		 */
		public function hidden() : void
		{
		}

		/**
		 *
		 */
		public function popupShown() : void
		{
		}

		/**
		 *
		 */
		public function popupHidden() : void
		{
		}

		/**
		 * @inheritDoc
		 */
		protected override function initialize() : void
		{
			super.initialize();

			_initialize();
			if( DeviceCapabilities.isPhone( STAGE ) ) _initializePhone();
			else _initializeTablet();
		}

		/**
		 * @inheritDoc
		 */
		protected override function draw() : void
		{
			super.draw();

			_draw();

			if( DeviceCapabilities.isPhone( STAGE ) ) _drawPhone();
			else _drawTablet();
		}

		/**
		 * @private
		 */
		protected function _initialize() : void
		{
		}

		/**
		 * @private
		 */
		protected function _initializePhone() : void
		{
		}

		/**
		 * @private
		 */
		protected function _initializeTablet() : void
		{
		}

		/**
		 * @private
		 */
		protected function _draw() : void
		{
		}

		/**
		 * @private
		 */
		protected function _drawPhone() : void
		{
		}

		/**
		 * @private
		 */
		protected function _drawTablet() : void
		{
		}

		/**
		 * @private
		 */
		protected function _goBack() : void
		{
			NAVIGATOR.showPreviousScreen();
		}
	}
}
