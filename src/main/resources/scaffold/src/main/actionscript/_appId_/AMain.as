package _appId_
{
	import _appId_.actors.NAVIGATOR;
	import _appId_.actors.STAGE;
	import _appId_.actors.STARLING;
	import _appId_.actors.STARLING_MAIN;
	import _appId_.actors.STARLING_STAGE;
	import _appId_.model.Config;
	import _appId_.theme.Fonts;
	import _appId_.utils.device.isDesktop;
	import _appId_.utils.displayMetrics.getDensityScale;
	import _appId_.view.StarlingMain;

	import flash.desktop.NativeApplication;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.display3D.Context3DRenderMode;
	import flash.events.Event;
	import flash.events.InvokeEvent;
	import flash.geom.Rectangle;
	import flash.system.Capabilities;

	import myLogger.DEFAULT_LOGGER;
	import myLogger.debug;

	import org.gestouch.core.Gestouch;
	import org.gestouch.extensions.starling.StarlingDisplayListAdapter;
	import org.gestouch.extensions.starling.StarlingTouchHitTester;
	import org.gestouch.input.NativeInputAdapter;

	import starling.core.Starling;
	import starling.display.DisplayObject;
	import starling.events.Event;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AMain extends Sprite
	{
		/**
		 *
		 */
		protected var _isActivated : Boolean = true;

		/**
		 *
		 */
		public function AMain()
		{
			NativeApplication.nativeApplication.addEventListener( InvokeEvent.INVOKE , _init , false , 0 , true );

			addEventListener( flash.events.Event.ACTIVATE , _onActivate , false , 0 , true );
			addEventListener( flash.events.Event.DEACTIVATE , _onDeactivate , false , 0 , true );
		}

		/**
		 *
		 */
		protected function _initDebugger() : void
		{
			DEFAULT_LOGGER.verbose = Capabilities.isDebugger;
		}

		/**
		 *
		 */
		protected function _initStage() : void
		{
			STAGE = stage;
			STAGE.align = StageAlign.TOP_LEFT;
			STAGE.scaleMode = StageScaleMode.NO_SCALE;
			STAGE.stageFocusRect = false;
		}

		/**
		 *
		 */
		protected function _initFonts() : void
		{
			Fonts;
		}

		/**
		 *
		 */
		protected function _initConstants() : void
		{
		}

		/**
		 *
		 */
		protected function _initSplashScreen() : void
		{
		}

		/**
		 *
		 */
		protected function _initStarling() : void
		{
			Starling.multitouchEnabled = true;
			Starling.handleLostContext = true;

			STARLING = new Starling( StarlingMain , stage , null , null , Context3DRenderMode.AUTO , "auto" );
			STARLING.showStats = Capabilities.isDebugger;
			STARLING.simulateMultitouch = isDesktop();
			STARLING.enableErrorChecking = Capabilities.isDebugger;
			STARLING.supportHighResolutions = isDesktop();
			STARLING_STAGE = STARLING.stage;

			_onStageResize();

			STARLING.addEventListener( starling.events.Event.CONTEXT3D_CREATE , _onStarlingContextCreate );
			STARLING.addEventListener( starling.events.Event.ROOT_CREATED , _onStarlingRootCreate );

			Gestouch.inputAdapter = new NativeInputAdapter( STAGE );
			Gestouch.addDisplayListAdapter( DisplayObject , new StarlingDisplayListAdapter() );
			Gestouch.addTouchHitTester( new StarlingTouchHitTester( STARLING ) , -1 );

			if( _isActivated ) STARLING.start();
		}

		/**
		 *
		 */
		protected function _onMainReady() : void
		{
			NAVIGATOR.showPath( Config.FIRST_SCREEN_PATH );
		}

		/**
		 *
		 */
		protected function _onActivate( e : flash.events.Event ) : void
		{
			_isActivated = true;

			if( STARLING != null ) STARLING.start();
		}

		/**
		 *
		 */
		protected function _onDeactivate( e : flash.events.Event ) : void
		{
			_isActivated = false;

			if( STARLING != null && !isDesktop() ) STARLING.stop( true );
		}

		/**
		 *
		 */
		protected function _init( e : InvokeEvent ) : void
		{
			_initDebugger();
			_initStage();
			_initFonts();
			_initConstants();
			_initSplashScreen();
			_initStarling();

			NativeApplication.nativeApplication.removeEventListener( InvokeEvent.INVOKE , _init );
		}

		/**
		 *
		 */
		protected function _onStarlingContextCreate( e : starling.events.Event ) : void
		{
			debug( this , "_onStarlingContextCreate" );

			STARLING.removeEventListener( starling.events.Event.CONTEXT3D_CREATE , _onStarlingContextCreate );
		}

		/**
		 *
		 */
		protected function _onStarlingRootCreate( e : starling.events.Event ) : void
		{
			debug( this , "_onStarlingRootCreate" );

			STAGE.addEventListener( flash.events.Event.RESIZE , _onStageResize , false , int.MAX_VALUE );

			if( STARLING_MAIN.isReady ) _onMainReady();
			else STARLING_MAIN.assetsComplete.add( _onMainReady );

			STARLING.removeEventListener( starling.events.Event.ROOT_CREATED , _onStarlingRootCreate );
		}

		/**
		 *
		 */
		protected function _onStageResize( e : flash.events.Event = null ) : void
		{
			var stageWidth : Number = STAGE.stageWidth;
			var stageHeight : Number = STAGE.stageHeight;
			var scale : Number = getDensityScale();

			STARLING_STAGE.stageWidth = stageWidth / scale;
			STARLING_STAGE.stageHeight = stageHeight / scale;

			STARLING.viewPort = new Rectangle( 0 , 0 , stageWidth , stageHeight );
		}
	}
}
