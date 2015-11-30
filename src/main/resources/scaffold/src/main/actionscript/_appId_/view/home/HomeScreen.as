package _appId_.view.home
{
	import _appId_.view.core.AScreen;

	import feathers.controls.Label;

	import myLogger.debug;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class HomeScreen extends AScreen
	{
		/**
		 *
		 */
		protected var _label : Label;

		/**
		 *
		 */
		public function HomeScreen()
		{
			super();

			debug( this , "Home" );
		}

		/**
		 * @inheritDoc
		 */
		override protected function _initialize() : void
		{
			super._initialize();

			_label = new Label();
			_label.text = "Hello World!";
			addChild( _label );
		}

		/**
		 * @inheritDoc
		 */
		override protected function _draw() : void
		{
			super._draw();

			if( isInvalid( INVALIDATION_FLAG_SIZE ) )
			{
				_label.validate();
				_label.x = ( actualWidth - _label.width ) / 2;
				_label.y = 100;
			}
		}
	}
}
