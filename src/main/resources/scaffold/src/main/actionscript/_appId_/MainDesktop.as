package _appId_
{
	import _appId_.actors.STAGE;

	import feathers.system.DeviceCapabilities;

	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.InvokeEvent;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	[SWF(width='1024' , height='768' , frameRate='60' , backgroundColor='0xffffff')]
	public class MainDesktop extends ASplashMain
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
			return STAGE.contentsScaleFactor == 2 ? "splash@x2.png" : "splash.png";
		}

		/**
		 * @inheritDoc
		 */
		override protected function _splashScreenLandscapeLoaded( e : Event ) : void
		{
			( _splashScreenLandscape.content as Bitmap ).smoothing = true;

			var w : Number = STAGE.stageWidth;
			var h : Number = STAGE.stageHeight;

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
