package _appId_.view.core
{
	import feathers.controls.ScrollScreen;
	import feathers.controls.StackScreenNavigator;

	import org.osflash.signals.Signal;

	/**
	 * Feathers ScrollScreen with ability to show hide by itself
	 */
	public class AScrollScreen extends ScrollScreen implements IShowHideScreen
	{
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
		public function AScrollScreen()
		{
			super();
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

			backButtonHandler = _goBack;
		}

		/**
		 *
		 */
		protected function _goBack() : void
		{
			if( _owner is StackScreenNavigator ) ( _owner as StackScreenNavigator ).popScreen();
		}
	}
}
