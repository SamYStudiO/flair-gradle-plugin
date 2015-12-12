package _appId_
{
	import _appId_.theme.Fonts;
	import _appId_.utils.device.isDesktop;
	import _appId_.utils.displayMetrics.getDensityScale;
	import _appId_.view.StarlingMain;

	import feathers.events.FeathersEventType;

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
	import myLogger.info;

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
		protected var _starling : Starling;

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
			stage.align = StageAlign.TOP_LEFT;
			stage.scaleMode = StageScaleMode.NO_SCALE;
			stage.stageFocusRect = false;
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
		protected function _initStarling() : void
		{
			Starling.multitouchEnabled = true;
			Starling.handleLostContext = true;

			_starling = new Starling( StarlingMain , stage , null , null , Context3DRenderMode.AUTO , "auto" );
			_starling.showStats = Capabilities.isDebugger;
			_starling.simulateMultitouch = isDesktop();
			_starling.enableErrorChecking = Capabilities.isDebugger;
			_starling.supportHighResolutions = isDesktop();

			_onStageResize();

			_starling.addEventListener( starling.events.Event.CONTEXT3D_CREATE , _onStarlingContextCreate );
			_starling.addEventListener( starling.events.Event.ROOT_CREATED , _onStarlingRootCreate );

			Gestouch.inputAdapter = new NativeInputAdapter( stage );
			Gestouch.addDisplayListAdapter( DisplayObject , new StarlingDisplayListAdapter() );
			Gestouch.addTouchHitTester( new StarlingTouchHitTester( _starling ) , -1 );

			if( _isActivated ) _starling.start();
		}

		/**
		 *
		 */
		protected function _onMainInitialized() : void
		{
			info( this , "_onMainInitialized" );

			_starling.root.removeEventListener( FeathersEventType.INITIALIZE , _onMainInitialized );
		}

		/**
		 *
		 */
		protected function _onActivate( e : flash.events.Event ) : void
		{
			info( this , "_onActivate" );

			_isActivated = true;

			if( _starling ) _starling.start();
		}

		/**
		 *
		 */
		protected function _onDeactivate( e : flash.events.Event ) : void
		{
			info( this , "_onDeactivate" );

			_isActivated = false;

			if( _starling && !isDesktop() ) _starling.stop( true );
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
			_initStarling();

			NativeApplication.nativeApplication.removeEventListener( InvokeEvent.INVOKE , _init );
		}

		/**
		 *
		 */
		protected function _onStarlingContextCreate( e : starling.events.Event ) : void
		{
			info( this , "_onStarlingContextCreate" );

			_starling.removeEventListener( starling.events.Event.CONTEXT3D_CREATE , _onStarlingContextCreate );
		}

		/**
		 *
		 */
		protected function _onStarlingRootCreate( e : starling.events.Event ) : void
		{
			info( this , "_onStarlingRootCreate" );

			_starling.removeEventListener( starling.events.Event.ROOT_CREATED , _onStarlingRootCreate );
			stage.addEventListener( flash.events.Event.RESIZE , _onStageResize , false , int.MAX_VALUE );
			_starling.root.addEventListener( FeathersEventType.INITIALIZE , _onMainInitialized );
		}

		/**
		 *
		 */
		protected function _onStageResize( e : flash.events.Event = null ) : void
		{
			var stageWidth : Number = stage.stageWidth;
			var stageHeight : Number = stage.stageHeight;

			info( this , "_onStageResize" , stageWidth , stageHeight );

			var scale : Number = getDensityScale();

			_starling.stage.stageWidth = stageWidth / scale;
			_starling.stage.stageHeight = stageHeight / scale;

			_starling.viewPort = new Rectangle( 0 , 0 , stageWidth , stageHeight );
		}
	}
}
