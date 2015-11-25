package _appId_.theme
{
	import _appId_.actors.STAGE;
	import _appId_.actors.STARLING;
	import _appId_.utils.DeviceInfos;

	import feathers.controls.text.StageTextTextEditor;
	import feathers.controls.text.TextFieldTextRenderer;
	import feathers.core.FeathersControl;
	import feathers.core.PopUpManager;
	import feathers.system.DeviceCapabilities;
	import feathers.themes.StyleNameFunctionTheme;

	import flash.display3D.Context3DProfile;
	import flash.filesystem.File;
	import flash.system.Capabilities;

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
		 * @private
		 */
		protected var _mockupPhoneSmallerSide : uint = 1440;

		/**
		 * @private
		 */
		protected var _mockupPhoneLargerSide : uint = 2560;

		/**
		 * @private
		 */
		protected var _mockupPhoneDpi : uint = 560;

		/**
		 * @private
		 */
		protected var _mockupTabletSmallerSide : uint = 1600;

		/**
		 * @private
		 */
		protected var _mockupTabletLargerSide : uint = 2560;

		/**
		 * @private
		 */
		protected var _mockupTabletDpi : uint = 280;

		/**
		 * @private
		 */
		protected var _minBucket4096Texture : String;

		/**
		 *
		 */
		public function Theme( minBucket4096Texture : String = DpiBucket.XHDPI )
		{
			_minBucket4096Texture = minBucket4096Texture;

			_initializeGlobals();
			_initializeStage();
			_initializeScale();
			_initializeStyleProviders();
		}

		/**
		 * @private
		 */
		protected function _initializeGlobals() : void
		{
			FeathersControl.defaultTextRendererFactory = __textRendererFactory;
			FeathersControl.defaultTextEditorFactory = __textEditorFactory;

			PopUpManager.overlayFactory = __popUpOverlayFactory;
		}

		/**
		 * @private
		 */
		protected function _initializeStage() : void
		{
			Starling.current.stage.color = 0xffffff;
			Starling.current.nativeStage.color = 0xffffff;
		}

		/**
		 * @private
		 */
		protected function _initializeScale() : void
		{
			DeviceCapabilities.tabletScreenMinimumInches = 5.5;

			var f : File = File.applicationDirectory.resolvePath( "resources" );
			var dpi : uint = CONFIG::ANDROID ? Capabilities.screenDPI : Capabilities.screenDPI * .95;
			var allowed4096Textures : Boolean = STARLING.profile != Context3DProfile.BASELINE && STARLING.profile != Context3DProfile.BASELINE_CONSTRAINED;
			var minBucket4096TextureDpi : uint = getBucketDpi( _minBucket4096Texture ) || uint.MAX_VALUE;

			switch( true )
			{
				case true :
					if( f.resolvePath( "drawable-" + DpiBucket.LDPI ).exists && ( allowed4096Textures || minBucket4096TextureDpi > 120 ) )
					{
						dpiBucketID = DpiBucket.LDPI;
						if( dpi <= 120 ) break;
					}
				case true :
					if( f.resolvePath( "drawable-" + DpiBucket.MDPI ).exists && ( allowed4096Textures || minBucket4096TextureDpi > 160 ) )
					{
						dpiBucketID = DpiBucket.MDPI;
						if( dpi <= 160 ) break;
					}
				case true :
					if( f.resolvePath( "drawable-" + DpiBucket.HDPI ).exists && ( allowed4096Textures || minBucket4096TextureDpi > 240 ) )
					{
						dpiBucketID = DpiBucket.HDPI;
						if( dpi <= 240 ) break;
					}
				case true :
					if( f.resolvePath( "drawable-" + DpiBucket.XHDPI ).exists && ( allowed4096Textures || minBucket4096TextureDpi > 320 ) )
					{
						dpiBucketID = DpiBucket.XHDPI;
						if( dpi <= 320 ) break;
					}
				case true :
					if( f.resolvePath( "drawable-" + DpiBucket.XXHDPI ).exists && ( allowed4096Textures || minBucket4096TextureDpi > 480 ) )
					{
						dpiBucketID = DpiBucket.XXHDPI;
						if( dpi <= 480 ) break;
					}
				case true :
					if( f.resolvePath( "drawable-" + DpiBucket.XXXHDPI ).exists && ( allowed4096Textures || minBucket4096TextureDpi > 640 ) )
					{
						dpiBucketID = DpiBucket.XXXHDPI;
						if( dpi <= 640 ) break;
					}
			}

			var mockupDeviceDpi : uint = DeviceCapabilities.isPhone( STAGE ) ? _mockupPhoneDpi : _mockupTabletDpi;
			var mockupDpiBucketID : String = DeviceCapabilities.isPhone( STAGE ) ? getDpiBucket( _mockupPhoneDpi ) : getDpiBucket( _mockupTabletDpi );
			var mockupDeviceSmallerSide : uint = DeviceCapabilities.isPhone( STAGE ) ? _mockupPhoneSmallerSide : _mockupTabletSmallerSide;
			var mockupDeviceLargerSide : uint = DeviceCapabilities.isPhone( STAGE ) ? _mockupPhoneLargerSide : _mockupTabletLargerSide;

			var w : Number = DeviceInfos.isDesktop() ? STAGE.stageWidth : STAGE.fullScreenWidth;
			var h : Number = DeviceInfos.isDesktop() ? STAGE.stageHeight : STAGE.fullScreenHeight;
			var currentDeviceSmallerSide : uint = Math.min( w , h );
			var currentDeviceLargerSide : uint = Math.max( w , h );

			var mockupBucketDpi : uint = getBucketDpi( mockupDpiBucketID );
			var currentBucketDpi : uint = getBucketDpi( dpiBucketID );

			dpiScale = DeviceCapabilities.dpi / mockupDeviceDpi;
			assetScale = dpiBucketID != null ? DeviceCapabilities.dpi / ( mockupDeviceDpi / ( mockupBucketDpi / currentBucketDpi ) ) : dpiScale;
			bucketScale = dpiBucketID != null ? currentBucketDpi / mockupBucketDpi : 1.0;

			var smallerSideScale : Number = currentDeviceSmallerSide / ( mockupDeviceSmallerSide * dpiScale );
			var largerSideScale : Number = currentDeviceLargerSide / ( mockupDeviceLargerSide * dpiScale );
			var pixelScale : Number = Math.min( smallerSideScale , largerSideScale );

			dpiScale *= pixelScale;
			assetScale *= pixelScale;
		}

		/**
		 * @private
		 */
		protected function _initializeStyleProviders() : void
		{
		}
	}
}
