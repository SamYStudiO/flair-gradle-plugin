package _appId_.view
{
	import _appId_.actors.STARLING_MAIN;
	import _appId_.theme.FeathersTheme;
	import _appId_.view.core.AAssetNavigatorPanelScreen;
	import _appId_.view.home.HomeScreen;

	import feathers.controls.ScreenNavigatorItem;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class StarlingMain extends AAssetNavigatorPanelScreen
	{
		/**
		 *
		 */
		public function StarlingMain()
		{
			super();

			STARLING_MAIN = this;
			_screenID = EnumScreen.MAIN;
			new FeathersTheme();
		}

		/**
		 * @inheritDoc
		 */
		override protected function _initialize() : void
		{
			super._initialize();

			addScreen( EnumScreen.HOME , new ScreenNavigatorItem( HomeScreen ) );
		}
	}
}
