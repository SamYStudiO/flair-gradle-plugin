package _appId_
{
	import feathers.system.DeviceCapabilities;

	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.InvokeEvent;

	[SWF(width='1024' , height='768' , frameRate='60' , backgroundColor='0xffffff')]
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */ public class MainDesktop extends ASplashMain
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
			DeviceCapabilities.dpi = 132;
			DeviceCapabilities.screenPixelWidth = stage.stageWidth;
			DeviceCapabilities.screenPixelHeight = stage.stageHeight;

			super._init( e );
		}
	}
}
