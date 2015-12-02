package _appId_.theme
{
	import feathers.controls.text.TextFieldTextRenderer;
	import feathers.core.FeathersControl;
	import feathers.core.PopUpManager;
	import feathers.themes.StyleNameFunctionTheme;

	import starling.core.Starling;
	import starling.display.DisplayObject;
	import starling.display.Quad;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class FeathersTheme extends StyleNameFunctionTheme
	{
		/**
		 *
		 */
		protected static function __textRendererFactory() : TextFieldTextRenderer
		{
			return new TextFieldTextRenderer();
		}

		/**
		 *
		 */
		protected static function __popUpOverlayFactory() : DisplayObject
		{
			var q : Quad = new Quad( 100 , 100 , 0x000000 );
			q.alpha = .8;

			return q;
		}

		/**
		 *
		 */
		public function FeathersTheme()
		{
			_initializeGlobals();
			_initializeStage();
		}

		/**
		 *
		 */
		protected function _initializeGlobals() : void
		{
			FeathersControl.defaultTextRendererFactory = __textRendererFactory;
			PopUpManager.overlayFactory = __popUpOverlayFactory;
		}

		/**
		 *
		 */
		protected function _initializeStage() : void
		{
			Starling.current.stage.color = 0xffffff;
			Starling.current.nativeStage.color = 0xffffff;
		}
	}
}
