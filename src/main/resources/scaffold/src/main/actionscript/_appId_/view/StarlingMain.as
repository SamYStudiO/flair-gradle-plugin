package _appId_.view
{
	import _appId_.resources.getString;
	import _appId_.theme.FeathersTheme;
	import _appId_.view.core.AAssetPanelScreen;
	import _appId_.view.home.HomeScreen;

	import feathers.controls.StackScreenNavigator;
	import feathers.controls.StackScreenNavigatorItem;
	import feathers.motion.Slide;

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

			title = "MAIN";

			_navigator = new StackScreenNavigator();
			_navigator.pushTransition = Slide.createSlideLeftTransition();
			_navigator.popTransition = Slide.createSlideRightTransition();
			_navigator.addScreen( EnumScreen.HOME , new StackScreenNavigatorItem( HomeScreen ) );
			_navigator.pushScreen( getString( "first_screen" ) );

			addChild( _navigator );
		}
	}
}
