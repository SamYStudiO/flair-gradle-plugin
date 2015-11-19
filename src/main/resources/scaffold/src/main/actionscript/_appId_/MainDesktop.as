package _appId_
{
	import feathers.system.DeviceCapabilities;

	import flash.events.InvokeEvent;

	[SWF(width='1024', height='768', frameRate='60', backgroundColor='0xffffff')]
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
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
		protected override function _init( e : InvokeEvent ) : void
		{
			DeviceCapabilities.dpi = 132;
			DeviceCapabilities.screenPixelWidth = stage.stageWidth;
			DeviceCapabilities.screenPixelHeight = stage.stageHeight;

			super._init( e );
		}
	}
}
