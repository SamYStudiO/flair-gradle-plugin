package _appId_
{
	import _appId_.actors.ORIENTATION_MANAGER;
	import _appId_.actors.STAGE;

	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.filesystem.File;
	import flash.filesystem.FileMode;
	import flash.filesystem.FileStream;
	import flash.geom.Matrix;
	import flash.utils.ByteArray;
	import flash.utils.setTimeout;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class ASplashMain extends AMain
	{
		/**
		 *
		 */
		protected var _defaultAutoOrients : Boolean;

		/**
		 *
		 */
		protected var _splashScreenContainer : Sprite;

		/**
		 *
		 */
		protected var _splashScreenPortrait : Loader;

		/**
		 *
		 */
		protected var _splashScreenLandscape : Loader;

		/**
		 *
		 */
		public function ASplashMain()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		override protected function _initStage() : void
		{
			super._initStage();

			_defaultAutoOrients = STAGE.autoOrients;

			if( STAGE.autoOrients ) ORIENTATION_MANAGER.startListeningForChange();
		}

		/**
		 * @inheritDoc
		 */
		override protected function _initSplashScreen() : void
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
		override protected function _onMainReady() : void
		{
			setTimeout( _cleanupSplashScreen , 1000 );

			STAGE.autoOrients = _defaultAutoOrients;
			if( STAGE.autoOrients ) ORIENTATION_MANAGER.updateStageOrientationFromDeviceOrientation();

			super._onMainReady();
		}

		/**
		 *
		 */
		protected function _getSplashScreen( portrait : Boolean ) : Loader
		{
			var loader : Loader = new Loader();
			loader.contentLoaderInfo.addEventListener( Event.COMPLETE , portrait ? _splashScreenPortraitLoaded : _splashScreenLandscapeLoaded , false , 0 , true );

			var filePath : String = _getSplashScreenFilePath( portrait );
			var file : File;

			if( filePath != null && filePath != "" ) file = File.applicationDirectory.resolvePath( filePath );

			if( file != null && file.exists )
			{
				var fs : FileStream = new FileStream();
				fs.open( file , FileMode.READ );

				var ba : ByteArray = new ByteArray();
				fs.readBytes( ba );

				loader.loadBytes( ba );

				fs.close();
			}

			return loader;
		}

		/**
		 *
		 */
		protected function _orientationChanged() : void
		{
			_splashScreenPortrait.visible = ORIENTATION_MANAGER.isDevicePortrait;
			_splashScreenLandscape.visible = ORIENTATION_MANAGER.isDeviceLandscape;

			var m : Matrix = ORIENTATION_MANAGER.deviceVSStageMatrix;
			m.tx *= STAGE.stageWidth;
			m.ty *= STAGE.stageHeight;

			_splashScreenContainer.transform.matrix = m;
		}

		/**
		 *
		 */
		protected function _getSplashScreenFilePath( portrait : Boolean ) : String
		{
			return null;
		}

		/**
		 *
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

			_splashScreenPortrait.contentLoaderInfo.removeEventListener( Event.COMPLETE , _splashScreenPortraitLoaded );
			_splashScreenLandscape.contentLoaderInfo.removeEventListener( Event.COMPLETE , _splashScreenLandscapeLoaded );

			_splashScreenContainer.removeChildren();

			STAGE.removeChild( _splashScreenContainer );

			_splashScreenPortrait = null;
			_splashScreenLandscape = null;
			_splashScreenContainer = null;

			ORIENTATION_MANAGER.deviceOrientationChanged.remove( _orientationChanged );
		}

		/**
		 *
		 */
		protected function _splashScreenPortraitLoaded( e : Event ) : void
		{
		}

		/**
		 *
		 */
		protected function _splashScreenLandscapeLoaded( e : Event ) : void
		{
		}
	}
}
