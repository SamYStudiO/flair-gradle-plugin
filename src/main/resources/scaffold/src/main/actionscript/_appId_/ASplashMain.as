package _appId_
{
	import _appId_.utils.OrientationManager;

	import flash.display.Loader;
	import flash.display.Sprite;
	import flash.events.Event;
	import flash.events.InvokeEvent;
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
		protected const _ORIENTATION_MANAGER : OrientationManager = OrientationManager.getInstance( stage );

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

			_defaultAutoOrients = stage.autoOrients;

			if( stage.autoOrients ) _ORIENTATION_MANAGER.startListeningForChange();
		}

		/**
		 * @inheritDoc
		 */
		override protected function _onMainInitialized() : void
		{
			setTimeout( _cleanupSplashScreen , 1000 );

			stage.autoOrients = _defaultAutoOrients;
			if( stage.autoOrients ) _ORIENTATION_MANAGER.updateStageOrientationFromDeviceOrientation();

			super._onMainInitialized();
		}

		/**
		 *
		 */
		protected function _initSplashScreen() : void
		{
			_splashScreenContainer = new Sprite();
			stage.addChild( _splashScreenContainer );

			_splashScreenPortrait = _getSplashScreen( true );
			_splashScreenPortrait.visible = _ORIENTATION_MANAGER.isDevicePortrait;
			_splashScreenContainer.addChild( _splashScreenPortrait );

			_splashScreenLandscape = _getSplashScreen( false );
			_splashScreenLandscape.visible = _ORIENTATION_MANAGER.isDeviceLandscape;
			_splashScreenContainer.addChild( _splashScreenLandscape );

			_ORIENTATION_MANAGER.deviceOrientationChanged.add( _orientationChanged );
		}

		/**
		 *
		 */
		protected function _getSplashScreen( portrait : Boolean ) : Loader
		{
			var loader : Loader = new Loader();
			loader.contentLoaderInfo.addEventListener( Event.COMPLETE , portrait ? _splashScreenPortraitLoaded : _splashScreenLandscapeLoaded );

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
			_splashScreenPortrait.visible = _ORIENTATION_MANAGER.isDevicePortrait;
			_splashScreenLandscape.visible = _ORIENTATION_MANAGER.isDeviceLandscape;

			var m : Matrix = _ORIENTATION_MANAGER.deviceVSStageMatrix;
			m.tx *= stage.stageWidth;
			m.ty *= stage.stageHeight;

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

			stage.removeChild( _splashScreenContainer );

			_splashScreenPortrait = null;
			_splashScreenLandscape = null;
			_splashScreenContainer = null;

			_ORIENTATION_MANAGER.deviceOrientationChanged.remove( _orientationChanged );
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

		/**
		 * @inheritDoc
		 */
		override protected function _init( e : InvokeEvent ) : void
		{
			super._init( e );

			_initSplashScreen();
		}
	}
}
