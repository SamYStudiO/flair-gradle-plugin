package _appId_.view.otherScreen
{
	import feathers.controls.Button;
	import feathers.controls.Screen;
	import feathers.controls.StackScreenNavigator;
	import feathers.layout.VerticalLayout;

	import myLogger.debug;

	import starling.events.Event;

	/**
	 * Example screen (you may modify or remove)
	 */
	public class OtherScreen extends Screen
	{
		/**
		 *
		 */
		protected var _button : Button;

		/**
		 *
		 */
		public function OtherScreen()
		{
			super();

			debug( this , "OtherScreen" );
		}

		/**
		 * @inheritDoc
		 */
		override protected function initialize() : void
		{
			super.initialize();

			var l : VerticalLayout = new VerticalLayout();
			l.horizontalAlign = VerticalLayout.HORIZONTAL_ALIGN_CENTER;
			l.verticalAlign = VerticalLayout.VERTICAL_ALIGN_MIDDLE;
			layout = l;

			_button = new Button();
			_button.label = R.string.world;
			_button.addEventListener( Event.TRIGGERED , function () : void
			{
				( _owner as StackScreenNavigator ).popScreen();
			} );
			addChild( _button );
		}
	}
}
