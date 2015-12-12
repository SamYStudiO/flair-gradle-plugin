package _appId_.view.home
{
	import _appId_.resources.getString;
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
		override protected function initialize() : void
		{
			super.initialize();

			_label = new Label();
			_label.text = getString( "hello" );
			addChild( _label );
		}

		/**
		 * @inheritDoc
		 */
		override protected function draw() : void
		{
			super.draw();

			if( isInvalid( INVALIDATION_FLAG_SIZE ) )
			{
				_label.validate();
				_label.x = ( actualWidth - _label.width ) / 2;
				_label.y = 100;
			}
		}
	}
}
