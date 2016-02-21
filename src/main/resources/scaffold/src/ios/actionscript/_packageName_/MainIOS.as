package _packageName_
{
	import feathers.utils.display.calculateScaleRatioToFill;

	import flash.display.Bitmap;
	import flash.events.Event;

	/**
	 *
	 */
	public class MainIOS extends AMain
	{
		/**
		 *
		 */
		public function MainIOS()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		override protected function _getSplashScreenFilePath( portrait : Boolean ) : String
		{
			var filePath : String;
			var stageWidth : Number = stage.fullScreenWidth;
			var stageHeight : Number = stage.fullScreenHeight;

			switch( true )
			{
				// iPad retina
				case stageWidth == 1536 || stageWidth == 2048 :
					filePath = portrait ? "Default-Portrait@2x.png" : "Default-Landscape@2x.png";
					break;
				// iPad
				case stageWidth == 768 || stageWidth == 1024 :
					filePath = portrait ? "Default-Portrait.png" : "Default-Landscape.png";
					break;
				// iPhone 6+
				case stageWidth == 1080 || stageWidth == 1920 :
					filePath = portrait ? "Default-414w-736h@3x.png" : "Default-Landscape-414w-736h@3x.png";
					break;
				// iPhone 6
				case stageWidth == 750 || stageWidth == 1334 :
					filePath = portrait ? "Default-375w-667h@2x.png" : "Default-Landscape-375w-667h@2x.png";
					break;
				// iPhone 5
				case ( stageWidth == 640 && stageHeight == 1136 ) || stageWidth == 1136 :
					filePath = portrait ? "Default-568h@2x.png" : "Default-Landscape-568h@2x.png";
					break;
				// iPhone 4
				case stageWidth == 640 || stageWidth == 960 :
					filePath = portrait ? "Default@2x.png" : "Default-Landscape-320h@2x.png";
					break;

				default :

					var minWidth : Number = Math.min( stageWidth , stageHeight );
					var maxHeight : Number = Math.max( stageWidth , stageHeight );
					var r : Number = minWidth / maxHeight;

					if( r > .7 ) filePath = portrait ? "Default-Portrait@2x.png" : "Default-Landscape@2x.png";
					else if( r > .6 ) filePath = portrait ? "Default-414w-736h@3x.png" : "Default-Landscape-414w-736h@3x.png";
					else filePath = portrait ? "Default@2x.png" : "Default-Landscape-320h@2x.png";
			}

			return filePath;
		}

		/**
		 * @inheritDoc
		 */
		override protected function _splashScreenPortraitLoaded( e : Event ) : void
		{
			( _splashScreenPortrait.content as Bitmap ).smoothing = true;

			var w : Number = _orientationManager.isStagePortrait ? stage.stageWidth : stage.stageHeight;
			var h : Number = _orientationManager.isStagePortrait ? stage.stageHeight : stage.stageWidth;

			var scale : Number = calculateScaleRatioToFill( _splashScreenPortrait.width , _splashScreenPortrait.height , w , h );

			_splashScreenPortrait.scaleX = _splashScreenPortrait.scaleY = scale;
			_splashScreenPortrait.x = Math.round( ( w - _splashScreenPortrait.width ) / 2 );
			_splashScreenPortrait.y = Math.round( ( h - _splashScreenPortrait.height ) / 2 );
		}

		/**
		 * @inheritDoc
		 */
		override protected function _splashScreenLandscapeLoaded( e : Event ) : void
		{
			( _splashScreenLandscape.content as Bitmap ).smoothing = true;

			var w : Number = _orientationManager.isStageLandscape ? stage.stageWidth : stage.stageHeight;
			var h : Number = _orientationManager.isStageLandscape ? stage.stageHeight : stage.stageWidth;

			var scale : Number = calculateScaleRatioToFill( _splashScreenLandscape.width , _splashScreenLandscape.height , w , h );

			_splashScreenLandscape.scaleX = _splashScreenLandscape.scaleY = scale;
			_splashScreenLandscape.x = Math.round( ( w - _splashScreenLandscape.width ) / 2 );
			_splashScreenLandscape.y = Math.round( ( h - _splashScreenLandscape.height ) / 2 );
		}
	}
}
