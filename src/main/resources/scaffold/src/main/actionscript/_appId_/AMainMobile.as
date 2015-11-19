package _appId_
{
	import _appId_.actors.ORIENTATION_MANAGER;
	import _appId_.actors.STAGE;
	import _appId_.utils.DeviceInfos;

	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.geom.Matrix;
	import flash.utils.ByteArray;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AMainMobile extends AMain
	{
		/**
		 * @private
		 */
		protected var _defaultAutoOrients : Boolean;

		/**
		 * @private
		 */
		protected var _splashScreenContainer : Sprite;

		/**
		 * @private
		 */
		protected var _splashScreenPortrait : Loader;

		/**
		 * @private
		 */
		protected var _splashScreenLandscape : Loader;

		/**
		 *
		 */
		public function AMainMobile()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		protected override function _initStage() : void
		{
			super._initStage();

			_defaultAutoOrients = STAGE.autoOrients;

			if( STAGE.autoOrients ) ORIENTATION_MANAGER.startListeningForChange();
		}

		/**
		 * @inheritDoc
		 */
		protected override function _initSplashScreen() : void
		{
			_splashScreenContainer = new Sprite();
			STAGE.addChild( _splashScreenContainer );

			_splashScreenPortrait = _getSplashScreen( true );
			_splashScreenPortrait.visible = ORIENTATION_MANAGER.isDevicePortrait;
			_splashScreenContainer.addChild( _splashScreenPortrait );

			_splashScreenLandscape = _getSplashScreen( false );
			_splashScreenLandscape.visible = ORIENTATION_MANAGER.isDeviceLandscape;
			_splashScreenContainer.addChild( _splashScreenLandscape );

			ORIENTATION_MANAGER.deviceOrientationChanged.add( _orientationChanged );
		}

		/**
		 * @inheritDoc
		 */
		protected override function _mainReady() : void
		{
			_cleanupSplashScreen();

			STAGE.autoOrients = _defaultAutoOrients;
			if( STAGE.autoOrients ) ORIENTATION_MANAGER.updateStageOrientationFromDeviceOrientation();

			super._mainReady();
		}

		/**
		 * @private
		 */
		protected function _getSplashScreen( portrait : Boolean ) : Loader
		{
			var loader : Loader = new Loader();
			loader.contentLoaderInfo.addEventListener( Event.COMPLETE, portrait ? _splashScreenPortraitLoaded : _splashScreenLandscapeLoaded, false, 0, true );

			var filePath : String = _getSplashScreenFilePath( portrait );
			var file : File;

			if( filePath != null && filePath != "" ) file = File.applicationDirectory.resolvePath( filePath );

			if( file != null && file.exists )
			{
				var fs : FileStream = new FileStream();
				fs.open( file, FileMode.READ );

				var ba : ByteArray = new ByteArray();
				fs.readBytes( ba );

				loader.loadBytes( ba );

				fs.close();
			}

			return loader;
		}

		/**
		 * @private
		 */
		protected function _orientationChanged() : void
		{
			_splashScreenPortrait.visible = ORIENTATION_MANAGER.isDevicePortrait;
			_splashScreenLandscape.visible = ORIENTATION_MANAGER.isDeviceLandscape;

			var m : Matrix = ORIENTATION_MANAGER.deviceVSStageMatrix;
			m.tx *= DeviceInfos.isDesktop() ? STAGE.stageWidth : STAGE.fullScreenWidth;
			m.ty *= DeviceInfos.isDesktop() ? STAGE.stageHeight : STAGE.fullScreenHeight;

			_splashScreenContainer.transform.matrix = m;
		}

		/**
		 * @private
		 */
		protected function _getSplashScreenFilePath( portrait : Boolean ) : String
		{
			return null;
		}

		/**
		 * @private
		 */
		protected function _cleanupSplashScreen() : void
		{
			try
			{
				_splashScreenPortrait.close();
			}
			catch( e : Error )
			{
			}

			try
			{
				_splashScreenLandscape.close();
			}
			catch( e : Error )
			{
			}

			_splashScreenPortrait.contentLoaderInfo.removeEventListener( Event.COMPLETE, _splashScreenPortraitLoaded );
			_splashScreenLandscape.contentLoaderInfo.removeEventListener( Event.COMPLETE, _splashScreenLandscapeLoaded );

			_splashScreenContainer.removeChildren();

			STAGE.removeChild( _splashScreenContainer );

			_splashScreenPortrait = null;
			_splashScreenLandscape = null;
			_splashScreenContainer = null;

			ORIENTATION_MANAGER.deviceOrientationChanged.remove( _orientationChanged );
		}

		/**
		 * @private
		 */
		protected function _splashScreenPortraitLoaded( e : Event ) : void
		{
		}

		/**
		 * @private
		 */
		protected function _splashScreenLandscapeLoaded( e : Event ) : void
		{
		}
	}
}
