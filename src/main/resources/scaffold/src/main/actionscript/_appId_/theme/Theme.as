package _appId_.theme
{
	import feathers.controls.text.StageTextTextEditor;
	import feathers.controls.text.TextFieldTextRenderer;
	import feathers.core.FeathersControl;
	import feathers.core.PopUpManager;
	import feathers.system.DeviceCapabilities;
	import feathers.themes.StyleNameFunctionTheme;

	import starling.core.Starling;
	import starling.display.DisplayObject;
	import starling.display.Quad;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class Theme extends StyleNameFunctionTheme
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
		protected static function __textEditorFactory() : StageTextTextEditor
		{
			return new StageTextTextEditor();
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
		public function Theme()
		{
			_initializeGlobals();
			_initializeStage();
			_initializeScale();
			_initializeStyleProviders();
		}

		/**
		 *
		 */
		protected function _initializeGlobals() : void
		{
			DeviceCapabilities.tabletScreenMinimumInches = 5.5;

			FeathersControl.defaultTextRendererFactory = __textRendererFactory;
			FeathersControl.defaultTextEditorFactory = __textEditorFactory;

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

		/**
		 *
		 */
		protected function _initializeScale() : void
		{

		}

		/**
		 *
		 */
		protected function _initializeStyleProviders() : void
		{
		}
	}
}
