package _appId_
{
	import _appId_.actors.ORIENTATION_MANAGER;
	import _appId_.actors.STAGE;
	import _appId_.utils.DeviceInfos;

	import feathers.system.DeviceCapabilities;
	import feathers.utils.display.calculateScaleRatioToFit;

	import flash.display.Bitmap;
	import flash.events.Event;
	import flash.events.InvokeEvent;

	import net.samystudio.density.Density;

	[SWF(width='1080', height='1920', frameRate='60', backgroundColor='0xffffff')]
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */ public class MainAndroid extends AMainMobile
	{
		/**
		 *
		 */
		public function MainAndroid()
		{
			super();
		}

		/**
		 * @inheritDoc
		 */
		protected override function _getSplashScreenFilePath( portrait : Boolean ) : String
		{
			var filePath : String;
			var stageWidth : Number = STAGE.fullScreenWidth;
			var stageHeight : Number = STAGE.fullScreenHeight;

			var max : Number = Math.max( stageWidth, stageHeight );
			var min : Number = Math.min( stageWidth, stageHeight );
			var pixels : Number = stageWidth * stageHeight;
			var n : Number = max / min;

			if( n > 1.72 ) // 16/9
			{
				if( pixels > 2073600 ) filePath = portrait ? "1440x2560.png" : "2560x1440.png";
				else if( pixels > 921600 ) filePath = portrait ? "1080x1920.png" : "1920x1080.png";
				else filePath = portrait ? "720x1280.png" : "1280x720.png";
			}
			else if( n > 1.63 && n <= 1.72 ) // wtf ratio :)
			{
				if( pixels > 983040 ) filePath = portrait ? "1536x2560.png" : "2560x1536.png";
				else filePath = portrait ? "768x1280.png" : "1280x768.png";
			}
			else if( n > 1.50 && n <= 1.63 ) // 16/10
			{
				if( pixels > 1024000 ) filePath = portrait ? "1600x2560.png" : "2560x1600.png";
				else filePath = portrait ? "800x1280.png" : "1280x800.png";
			}
			else // 4/3
			{
				if( pixels > 1024000 ) filePath = portrait ? "1536x2048.png" : "2048x1536.png";
				else filePath = portrait ? "768x1024.png" : "1024x768.png";
			}

			return filePath;
		}

		/**
		 * @inheritDoc
		 */
		protected override function _init( e : InvokeEvent ) : void
		{
			if( DeviceInfos.isDesktop() ) DeviceCapabilities.dpi = uint( e.arguments );
			else if( Density.isSupported ) DeviceCapabilities.dpi = Density.service.xdpi;

			super._init( e );
		}

		/**
		 * @inheritDoc
		 */
		protected override function _splashScreenPortraitLoaded( e : Event ) : void
		{
			( _splashScreenPortrait.content as Bitmap ).smoothing = true;

			var w : Number = ORIENTATION_MANAGER.isStagePortrait ? STAGE.fullScreenWidth : STAGE.fullScreenHeight;
			var h : Number = ORIENTATION_MANAGER.isStagePortrait ? STAGE.fullScreenHeight : STAGE.fullScreenWidth;

			var scale : Number = calculateScaleRatioToFit( _splashScreenPortrait.width, _splashScreenPortrait.height, w, h );

			_splashScreenPortrait.scaleX = _splashScreenPortrait.scaleY = scale;
			_splashScreenPortrait.x = Math.round( ( w - _splashScreenPortrait.width ) / 2 );
			_splashScreenPortrait.y = Math.round( ( h - _splashScreenPortrait.height ) / 2 );
		}

		/**
		 * @inheritDoc
		 */
		protected override function _splashScreenLandscapeLoaded( e : Event ) : void
		{
			( _splashScreenLandscape.content as Bitmap ).smoothing = true;

			var w : Number = ORIENTATION_MANAGER.isStageLandscape ? STAGE.fullScreenWidth : STAGE.fullScreenHeight;
			var h : Number = ORIENTATION_MANAGER.isStageLandscape ? STAGE.fullScreenHeight : STAGE.fullScreenWidth;

			var scale : Number = calculateScaleRatioToFit( _splashScreenLandscape.width, _splashScreenLandscape.height, w, h );

			_splashScreenLandscape.scaleX = _splashScreenLandscape.scaleY = scale;
			_splashScreenLandscape.x = Math.round( ( w - _splashScreenLandscape.width ) / 2 );
			_splashScreenLandscape.y = Math.round( ( h - _splashScreenLandscape.height ) / 2 );
		}
	}
}
