package _appId_.view
{
	import _appId_.actors.STARLING_MAIN;
	import _appId_.theme.Theme;
	import _appId_.theme.dpiBucketID;
	import _appId_.utils.DeviceInfos;
	import _appId_.view.home.HomeScreen;

	import feathers.controls.ScreenNavigatorItem;

	import flash.filesystem.File;

	import starling.textures.TextureOptions;

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

			addScreen( EnumScreen.HOME, new ScreenNavigatorItem( HomeScreen ) );
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
				_assets.enqueue( File.applicationDirectory.resolvePath( "assets" ) );
			}
			else
			{
				if( DeviceInfos.isIOS() )
				{
					_assets.enqueue( File.applicationDirectory.resolvePath( dpiBucketID ) );
					_assets.enqueueWithName( File.applicationDirectory.resolvePath( "nodpi/global_nodpi.png" ), null, new TextureOptions( 1 ) );
					_assets.enqueue( File.applicationDirectory.resolvePath( "nodpi/global_nodpi.xml" ) );
					_assets.enqueue( File.applicationDirectory.resolvePath( "xml/" ) );
				}
				else
				{
					_assets.enqueue( File.applicationDirectory.resolvePath( "assets/medias/" + dpiBucketID ) );
					_assets.enqueueWithName( File.applicationDirectory.resolvePath( "assets/medias/nodpi/global_nodpi.png" ), null, new TextureOptions( 1 ) );
					_assets.enqueue( File.applicationDirectory.resolvePath( "assets/medias/nodpi/global_nodpi.xml" ) );
					_assets.enqueue( File.applicationDirectory.resolvePath( "assets/xml/" ) );
				}
			}
		}
	}
}
