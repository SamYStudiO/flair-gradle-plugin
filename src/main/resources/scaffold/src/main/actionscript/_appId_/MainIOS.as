package _appId_
{
	import _appId_.actors.STAGE;
	import _appId_.utils.DeviceInfos;

	import feathers.system.DeviceCapabilities;

	import flash.events.InvokeEvent;

	[SWF(width='640', height='1136', frameRate='60', backgroundColor='0xffffff')]
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */ public class MainIOS extends AMainMobile
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
		protected override function _getSplashScreenFilePath( portrait : Boolean ) : String
		{
			var filePath : String;
			var stageWidth : Number = STAGE.fullScreenWidth;
			var stageHeight : Number = STAGE.fullScreenHeight;

			switch( true )
			{
				// ipad retina
				case stageWidth == 1536 || stageWidth == 2048 :
					filePath = portrait ? "Default-Portrait@2x.png" : "Default-Landscape@2x.png";
					break;
				// ipad
				case stageWidth == 768 || stageWidth == 1024 :
					filePath = portrait ? "Default-Portrait.png" : "Default-Landscape.png";
					break;
				// iphone 6+
				case stageWidth == 1080 || stageWidth == 1920 :
					filePath = portrait ? "Default-414w-736h@3x.png" : "Default-414w-736h-Landscape@3x.png";
					break;
				// iphone 6
				case stageWidth == 750 || stageWidth == 1334 :
					filePath = portrait ? "Default-375w-667h@2x.png" : "Default-375w-667h-Landscape@2x.png";
					break;
				// iphone 5
				case ( stageWidth == 640 && stageHeight == 1136 ) || stageWidth == 1136 :
					filePath = portrait ? "Default-568h@2x.png" : "Default-568h-Landscape@2x.png";
					break;
				// iphone 4
				case stageWidth == 640 || stageWidth == 960 :
					filePath = portrait ? "Default@2x.png" : "Default-Phone-Landscape@2x.png";
					break;
				// iphone
				case stageWidth == 320 || stageWidth == 480 :
					filePath = portrait ? "Default.png" : "Default-Phone-Landscape.png";
					break;
			}

			return filePath;
		}

		/**
		 * @inheritDoc
		 */
		protected override function _init( e : InvokeEvent ) : void
		{
			if( DeviceInfos.isDesktop() ) DeviceCapabilities.dpi = uint( e.arguments );

			super._init( e );
		}
	}
}
