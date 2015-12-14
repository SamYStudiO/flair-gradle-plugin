package _appId_.view
{
	import _appId_.resources.getString;
	import _appId_.theme.FeathersTheme;
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
	 * @author SamYStudiO ( contact@samystudio.net )
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

			_screenID = EnumScreen.MAIN;
		}

		/**
		 * @inheritDoc
		 */
		override protected function initialize() : void
		{
			super.initialize();

			new FeathersTheme();

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
			_navigator.pushScreen( getString( "first_screen" ) );
			_navigator.layoutData = new AnchorLayoutData( 0 , 0 , 0 , 0 );

			addChild( _navigator );
		}
	}
}
