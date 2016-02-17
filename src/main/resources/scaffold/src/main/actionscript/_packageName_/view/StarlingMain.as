package _packageName_.view
{
	import _packageName_.view.home.HomeScreen;
	import _packageName_.view.otherScreen.OtherScreen;

	import feathers.controls.StackScreenNavigator;
	import feathers.controls.StackScreenNavigatorItem;
	import feathers.layout.AnchorLayout;
	import feathers.layout.AnchorLayoutData;
	import feathers.motion.Slide;

	import flair.controls.AResourcePanelScreen;

	import starling.display.Quad;

	/**
	 * App root screen, you should edit this to your convenience, depending on your needs you may extends
	 * AResourceScreen, AResourceScrollScreen or AResourcePanelScreen, each one will manage resources for you.
	 */
	public class StarlingMain extends AResourcePanelScreen
	{
		/**
		 *
		 */
		private var _navigator : StackScreenNavigator;

		/**
		 *
		 */
		public function StarlingMain()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		override protected function initialize() : void
		{
			super.initialize();

			autoSizeMode = AUTO_SIZE_MODE_STAGE;
			layout = new AnchorLayout();

			title = "Header";
			headerProperties.backgroundSkin = new Quad( 1 , 1 , 0x9999ff );

			footerFactory = defaultHeaderFactory;
			footerProperties.backgroundSkin = new Quad( 1 , 1 , 0xff9999 );
			footerProperties.title = "Footer";

			_navigator = new StackScreenNavigator();
			_navigator.pushTransition = Slide.createSlideLeftTransition();
			_navigator.popTransition = Slide.createSlideRightTransition();
			_navigator.addScreen( ScreenID.HOME , new StackScreenNavigatorItem( HomeScreen ) );
			_navigator.addScreen( ScreenID.OTHER_SCREEN , new StackScreenNavigatorItem( OtherScreen ) );
			_navigator.pushScreen( R.string.first_screen );
			_navigator.layoutData = new AnchorLayoutData( 0 , 0 , 0 , 0 );

			addChild( _navigator );
		}
	}
}
