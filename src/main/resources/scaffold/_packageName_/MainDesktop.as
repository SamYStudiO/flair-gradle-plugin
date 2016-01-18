package _packageName_
{
	import feathers.system.DeviceCapabilities;

	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.InvokeEvent;

	[SWF(width='1024' , height='768' , frameRate='60' , backgroundColor='0xffffff')]
	/**
	 *
	 */ public class MainDesktop extends AMain
	{
		/**
		 *
		 */
		public function MainDesktop()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		override protected function _getSplashScreenFilePath( portrait : Boolean ) : String
		{
			return stage.contentsScaleFactor == 2 ? "splash@x2.png" : "splash.png";
		}

		/**
		 * @inheritDoc
		 */
		override protected function _splashScreenLandscapeLoaded( e : Event ) : void
		{
			( _splashScreenLandscape.content as Bitmap ).smoothing = true;

			var w : Number = stage.stageWidth;
			var h : Number = stage.stageHeight;

			_splashScreenLandscape.x = Math.round( ( w - _splashScreenLandscape.width ) / 2 );
			_splashScreenLandscape.y = Math.round( ( h - _splashScreenLandscape.height ) / 2 );
		}

		/**
		 * @inheritDoc
		 */
		override protected function _init( e : InvokeEvent ) : void
		{
			// use custom dpi, you may adjust this value to match the scale you want for desktop
			DeviceCapabilities.dpi = 160;

			super._init( e );
		}
	}
}
