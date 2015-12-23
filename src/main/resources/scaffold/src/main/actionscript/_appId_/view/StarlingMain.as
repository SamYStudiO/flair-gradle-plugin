package _appId_.view
{
	import _appId_.view.core.AAssetPanelScreen;
	import _appId_.view.home.HomeScreen;
	import _appId_.view.otherScreen.OtherScreen;

	import feathers.controls.StackScreenNavigator;
	import feathers.controls.StackScreenNavigatorItem;
	import feathers.layout.AnchorLayout;
	import feathers.layout.AnchorLayoutData;
	import feathers.motion.Slide;

	import starling.display.Quad;

	/**
	 * App root screen, you should edit this to your convenience, depending on your needs you may extends
	 * AAssetScreen, AAssetScrollScreen or AAssetPanelScreen, each one will manage resources for you.
	 */
	public class StarlingMain extends AAssetPanelScreen
	{
		/**
		 *
		 */
		protected var _navigator : StackScreenNavigator;

		/**
		 *
		 */
		public function StarlingMain()
		{
			super();

			//Top root screen id, DO NOT modify
			_screenID = EnumScreen.MAIN;
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
			_navigator.addScreen( EnumScreen.HOME , new StackScreenNavigatorItem( HomeScreen ) );
			_navigator.addScreen( EnumScreen.OTHER_SCREEN , new StackScreenNavigatorItem( OtherScreen ) );
			_navigator.pushScreen( R.string.first_screen );
			_navigator.layoutData = new AnchorLayoutData( 0 , 0 , 0 , 0 );

			addChild( _navigator );
		}
	}
}
