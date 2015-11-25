package _appId_
{
	import _appId_.actors.NAVIGATOR;
	import _appId_.actors.STAGE;
	import _appId_.actors.STARLING;
	import _appId_.actors.STARLING_MAIN;
	import _appId_.actors.STARLING_STAGE;
	import _appId_.model.Config;
	import _appId_.theme.Fonts;
	import _appId_.utils.DeviceInfos;
	import _appId_.view.StarlingMain;

	import flash.desktop.NativeApplication;
	import flash.display.Sprite;
	import flash.display.StageAlign;
	import flash.display.StageScaleMode;
	import flash.display3D.Context3DRenderMode;
	import flash.events.Event;
	import flash.events.InvokeEvent;
	import flash.geom.Rectangle;

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
		 * @private
		 */
		protected var _isActivated : Boolean = true;

		/**
		 *
		 */
		public function AMain()
		{
			NativeApplication.nativeApplication.addEventListener( InvokeEvent.INVOKE , _init , false , 0 , true );

			addEventListener( flash.events.Event.ACTIVATE , _activated , false , 0 , true );
			addEventListener( flash.events.Event.DEACTIVATE , _deactivated , false , 0 , true );
		}

		/**
		 * @private
		 */
		protected function _initDebugger() : void
		{
			DEFAULT_LOGGER.verbose = Boolean( CONFIG::DEBUG );
		}

		/**
		 * @private
		 */
		protected function _initStage() : void
		{
			STAGE = stage;
			STAGE.align = StageAlign.TOP_LEFT;
			STAGE.scaleMode = StageScaleMode.NO_SCALE;
			STAGE.stageFocusRect = false;
		}

		/**
		 * @private
		 */
		protected function _initFonts() : void
		{
			Fonts;
		}

		/**
		 * @private
		 */
		protected function _initConstants() : void
		{
		}

		/**
		 * @private
		 */
		protected function _initSplashScreen() : void
		{
		}

		/**
		 * @private
		 */
		protected function _initStarling() : void
		{
			Starling.multitouchEnabled = true;
			Starling.handleLostContext = true;

			STARLING = new Starling( StarlingMain , stage , null , null , Context3DRenderMode.AUTO , "auto" );
			STARLING.showStats = Boolean( CONFIG::DEBUG );
			STARLING.simulateMultitouch = Boolean( CONFIG::DEBUG );
			STARLING.enableErrorChecking = Boolean( CONFIG::DEBUG );
			STARLING_STAGE = STARLING.stage;

			STARLING.addEventListener( starling.events.Event.CONTEXT3D_CREATE , _starlingContextCreated );
			STARLING.addEventListener( starling.events.Event.ROOT_CREATED , _starlingRootCreated );

			Gestouch.inputAdapter = new NativeInputAdapter( STAGE );
			Gestouch.addDisplayListAdapter( DisplayObject , new StarlingDisplayListAdapter() );
			Gestouch.addTouchHitTester( new StarlingTouchHitTester( STARLING ) , -1 );

			if( _isActivated ) STARLING.start();
		}

		/**
		 * @private
		 */
		protected function _mainReady() : void
		{
			NAVIGATOR.showPath( Config.FIRST_SCREEN_PATH );
		}

		/**
		 * @private
		 */
		protected function _activated( e : flash.events.Event ) : void
		{
			_isActivated = true;

			if( STARLING != null ) STARLING.start();
		}

		/**
		 * @private
		 */
		protected function _deactivated( e : flash.events.Event ) : void
		{
			_isActivated = false;

			if( STARLING != null && !DeviceInfos.isDesktop() ) STARLING.stop( true );
		}

		/**
		 * @private
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
		 * @private
		 */
		protected function _starlingContextCreated( e : starling.events.Event ) : void
		{
			debug( this , "_starlingContextCreated" );

			STARLING.removeEventListener( starling.events.Event.CONTEXT3D_CREATE , _starlingContextCreated );
		}

		/**
		 * @private
		 */
		protected function _starlingRootCreated( e : starling.events.Event ) : void
		{
			debug( this , "_starlingRootCreated" );

			STAGE.addEventListener( flash.events.Event.RESIZE , _stageResized , false , int.MAX_VALUE );
			_stageResized();

			if( STARLING_MAIN.isReady ) _mainReady();
			else STARLING_MAIN.assetsComplete.add( _mainReady );

			STARLING.removeEventListener( starling.events.Event.ROOT_CREATED , _starlingRootCreated );
		}

		/**
		 * @private
		 */
		protected function _stageResized( e : flash.events.Event = null ) : void
		{
			var stageWidth : Number = DeviceInfos.isDesktop() ? STAGE.stageWidth : STAGE.fullScreenWidth;
			var stageHeight : Number = DeviceInfos.isDesktop() ? STAGE.stageHeight : STAGE.fullScreenHeight;

			STARLING_STAGE.stageWidth = stageWidth;
			STARLING_STAGE.stageHeight = stageHeight;

			STARLING.viewPort = new Rectangle( 0 , 0 , stageWidth , stageHeight );

			STARLING_MAIN.setSize( stageWidth , stageHeight );
		}
	}
}
