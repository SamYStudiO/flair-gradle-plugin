package _appId_.view
{
	import _appId_.actors.STARLING_MAIN;
	import _appId_.theme.Theme;
	import _appId_.theme.dpiBucketID;
	import _appId_.view.home.HomeScreen;

	import feathers.controls.ScreenNavigatorItem;

	import flash.filesystem.File;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class StarlingMain extends AAssetsNavigatorScreen
	{
		/**
		 *
		 */
		public function StarlingMain()
		{
			super();

			STARLING_MAIN = this;
			_screenID = EnumScreen.MAIN;
			new Theme();
		}

		/**
		 * @inheritDoc
		 */
		protected override function _initialize() : void
		{
			super._initialize();

			addScreen( EnumScreen.HOME , new ScreenNavigatorItem( HomeScreen ) );
		}

		/**
		 * @inheritDoc
		 */
		protected override function _draw() : void
		{
			super._draw();

			if( isInvalid( INVALIDATION_FLAG_SIZE ) )
			{

			}
		}

		/**
		 * @inheritDoc
		 */
		protected override function _addAssets() : void
		{
			if( dpiBucketID == null )
			{
				_assets.enqueue( File.applicationDirectory.resolvePath( "resources" ) );
			}
			else
			{
				_assets.enqueue( File.applicationDirectory.resolvePath( "resources/drawable-" + dpiBucketID ) );
				_assets.enqueue( File.applicationDirectory.resolvePath( "resources/values" ) );
			}
		}
	}
}
