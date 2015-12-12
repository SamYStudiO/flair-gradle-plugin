/*
Feathers
Copyright 2012-2015 Bowler Hat LLC. All Rights Reserved.

This program is free software. You can redistribute and/or modify it in
accordance with the terms of the accompanying license agreement.
*/
package feathers.controls
{
	import feathers.core.FeathersControl;
	import feathers.events.FeathersEventType;
	import feathers.utils.geom.matrixToScaleX;
	import feathers.utils.geom.matrixToScaleY;

	import flash.errors.IllegalOperationError;
	import flash.events.ErrorEvent;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.utils.getDefinitionByName;

	import starling.core.RenderSupport;
	import starling.core.Starling;
	import starling.display.DisplayObject;
	import starling.events.Event;
	import starling.utils.MatrixUtil;

	/**
	 * Dispatched when a URL has finished loading with <code>loadURL()</code> or a
	 * string has finished loading with <code>loadString()</code>.
	 *
	 * <p>The properties of the event object have the following values:</p>
	 * <table class="innertable">
	 * <tr><th>Property</th><th>Value</th></tr>
	 * <tr><td><code>bubbles</code></td><td>false</td></tr>
	 * <tr><td><code>currentTarget</code></td><td>The Object that defines the
	 *   event listener that handles the event. For example, if you use
	 *   <code>myButton.addEventListener()</code> to register an event listener,
	 *   myButton is the value of the <code>currentTarget</code>.</td></tr>
	 * <tr><td><code>data</code></td><td>null</td></tr>
	 * <tr><td><code>target</code></td><td>The Object that dispatched the event;
	 *   it is not always the Object listening for the event. Use the
	 *   <code>currentTarget</code> property to always access the Object
	 *   listening for the event.</td></tr>
	 * </table>
	 *
	 * @eventType starling.events.Event.COMPLETE
	 *
	 * @see #loadURL()
	 * @see #loadString()
	 */
	[Event(name="complete",type="starling.events.Event")]

	/**
	 * Indicates that the <code>location</code> property has changed.
	 *
	 * <p>The properties of the event object have the following values:</p>
	 * <table class="innertable">
	 * <tr><th>Property</th><th>Value</th></tr>
	 * <tr><td><code>bubbles</code></td><td>false</td></tr>
	 * <tr><td><code>currentTarget</code></td><td>The Object that defines the
	 *   event listener that handles the event. For example, if you use
	 *   <code>myButton.addEventListener()</code> to register an event listener,
	 *   myButton is the value of the <code>currentTarget</code>.</td></tr>
	 * <tr><td><code>data</code></td><td>null</td></tr>
	 * <tr><td><code>target</code></td><td>The Object that dispatched the event;
	 *   it is not always the Object listening for the event. Use the
	 *   <code>currentTarget</code> property to always access the Object
	 *   listening for the event.</td></tr>
	 * </table>
	 *
	 * @see #location
	 * 
	 * @eventType feathers.events.FeathersEventType.LOCATION_CHANGE
	 */
	[Event(name="locationChange",type="starling.events.Event")]

	/**
	 * Indicates that an error occurred in the <code>StageWebView</code>.
	 *
	 * <p>The properties of the event object have the following values:</p>
	 * <table class="innertable">
	 * <tr><th>Property</th><th>Value</th></tr>
	 * <tr><td><code>bubbles</code></td><td>false</td></tr>
	 * <tr><td><code>currentTarget</code></td><td>The Object that defines the
	 *   event listener that handles the event. For example, if you use
	 *   <code>myButton.addEventListener()</code> to register an event listener,
	 *   myButton is the value of the <code>currentTarget</code>.</td></tr>
	 * <tr><td><code>data</code></td><td>The <code>flash.events.ErrorEvent</code>
	 *   dispatched by the <code>StageWebView</code>.</td></tr>
	 * <tr><td><code>target</code></td><td>The Object that dispatched the event;
	 *   it is not always the Object listening for the event. Use the
	 *   <code>currentTarget</code> property to always access the Object
	 *   listening for the event.</td></tr>
	 * </table>
	 *
	 * @eventType feathers.events.FeathersEventType.ERROR
	 */
	[Event(name="error",type="starling.events.Event")]

	/**
	 * A Feathers component that displays a web browser in Adobe AIR, using the
	 * <code>flash.media.StageWebView</code> class.
	 *
	 * <p>Warning: This component is only compatible with Adobe AIR. It cannot
	 * be used with Adobe Flash Player in a web browser.</p>
	 *
	 * @see ../../../help/web-view.html How to use the Feathers WebView component
	 */
	public class WebView extends FeathersControl
	{
		/**
		 * @private
		 */
		private static const HELPER_MATRIX:Matrix = new Matrix();

		/**
		 * @private
		 */
		private static const HELPER_POINT:Point = new Point();

		/**
		 * @private
		 */
		protected static const STAGE_WEB_VIEW_NOT_SUPPORTED_ERROR:String = "Feathers WebView is only supported in Adobe AIR. It cannot be used in Adobe Flash Player.";

		/**
		 * @private
		 */
		protected static const USE_NATIVE_ERROR:String = "The useNative property may only be set before the WebView component validates for the first time.";

		/**
		 * @private
		 */
		protected static const DEFAULT_SIZE:Number = 320;

		/**
		 * @private
		 */
		protected static const STAGE_WEB_VIEW_FULLY_QUALIFIED_CLASS_NAME:String = "flash.media.StageWebView";

		/**
		 * @private
		 */
		protected static var STAGE_WEB_VIEW_CLASS:Class;

		/**
		 * Indicates if this component is supported on the current platform.
		 */
		public static function get isSupported():Boolean
		{
			if(!STAGE_WEB_VIEW_CLASS)
			{
				try
				{
					STAGE_WEB_VIEW_CLASS = Class(getDefinitionByName(STAGE_WEB_VIEW_FULLY_QUALIFIED_CLASS_NAME));
				}
				catch(error:Error)
				{
					return false;
				}
			}
			return STAGE_WEB_VIEW_CLASS.isSupported;
		}

		/**
		 * Constructor.
		 */
		public function WebView()
		{
			this.addEventListener(starling.events.Event.ADDED_TO_STAGE, webView_addedToStageHandler);
			this.addEventListener(starling.events.Event.REMOVED_FROM_STAGE, webView_removedFromStageHandler);
		}

		/**
		 * @private
		 */
		protected var stageWebView:Object;

		/**
		 * @private
		 */
		protected var _useNative:Boolean = false;

		/**
		 * Determines if the system native web browser control is used or if
		 * Adobe AIR's embedded version of the WebKit engine is used.
		 *
		 * <p>Note: Although it is not prohibited, with some content, failures can occur when the same process uses both the embedded and the system WebKit, so it is recommended that all StageWebViews in a given application be constructed with the same value for useNative. In addition, as HTMLLoader depends on the embedded WebKit, applications using HTMLLoader should only construct StageWebViews with useNative set to false.</p>
		 */
		public function get useNative():Boolean
		{
			return this._useNative;
		}

		/**
		 * @private
		 */
		public function set useNative(value:Boolean):void
		{
			if(this.isCreated)
			{
				throw new IllegalOperationError(USE_NATIVE_ERROR);
			}
			this._useNative = value;
		}

		/**
		 * The URL of the currently loaded page.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#location Full description of flash.media.StageWebView.location in Adobe's Flash Platform API Reference
		 */
		public function get location():String
		{
			if(this.stageWebView)
			{
				return this.stageWebView.location;
			}
			return null;
		}

		/**
		 * The title of the currently loaded page.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#title Full description of flash.media.StageWebView.title in Adobe's Flash Platform API Reference
		 */
		public function get title():String
		{
			if(this.stageWebView)
			{
				return this.stageWebView.title;
			}
			return null;
		}

		/**
		 * Indicates if the web view can navigate back in its history.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#isHistoryBackEnabled Full description of flash.media.StageWebView.isHistoryBackEnabled in Adobe's Flash Platform API Reference
		 */
		public function get isHistoryBackEnabled():Boolean
		{
			if(this.stageWebView)
			{
				return this.stageWebView.isHistoryBackEnabled;
			}
			return false;
		}

		/**
		 * Indicates if the web view can navigate forward in its history.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#isHistoryForwardEnabled Full description of flash.media.StageWebView.isHistoryForwardEnabled in Adobe's Flash Platform API Reference
		 */
		public function get isHistoryForwardEnabled():Boolean
		{
			if(this.stageWebView)
			{
				return this.stageWebView.isHistoryForwardEnabled;
			}
			return false;
		}

		/**
		 * @private
		 */
		override public function dispose():void
		{
			if(this.stageWebView)
			{
				this.stageWebView.stage = null;
				this.stageWebView.dispose();
				this.stageWebView = null;
			}
			super.dispose();
		}

		/**
		 * @private
		 */
		override public function render(support:RenderSupport, parentAlpha:Number):void
		{
			this.refreshViewPort();
			super.render(support, parentAlpha);
		}

		/**
		 * Loads the specified URL.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#loadURL() Full description of flash.media.StageWebView.loadURL() in Adobe's Flash Platform API Reference
		 */
		public function loadURL(url:String):void
		{
			this.validate();
			this.stageWebView.loadURL(url);
		}

		/**
		 * Renders the specified HTML or XHTML string.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#loadString() Full description of flash.media.StageWebView.loadString() in Adobe's Flash Platform API Reference
		 */
		public function loadString(text:String, mimeType:String = "text/html"):void
		{
			this.validate();
			this.stageWebView.loadString(text, mimeType);
		}

		/**
		 * Stops the current page from loading.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#stop() Full description of flash.media.StageWebView.stop() in Adobe's Flash Platform API Reference
		 */
		public function stop():void
		{
			this.validate();
			this.stageWebView.stop();
		}

		/**
		 * Reloads the currently loaded page.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#reload() Full description of flash.media.StageWebView.reload() in Adobe's Flash Platform API Reference
		 */
		public function reload():void
		{
			this.validate();
			this.stageWebView.reload();
		}

		/**
		 * Navigates to the previous page in the browsing history.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#historyBack() Full description of flash.media.StageWebView.historyBack() in Adobe's Flash Platform API Reference
		 * @see #isHistoryBackEnabled
		 */
		public function historyBack():void
		{
			this.validate();
			this.stageWebView.historyBack();
		}

		/**
		 * Navigates to the next page in the browsing history.
		 *
		 * @see http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/flash/media/StageWebView.html#historyForward() Full description of flash.media.StageWebView.historyForward() in Adobe's Flash Platform API Reference
		 * @see #isHistoryForwardEnabled
		 */
		public function historyForward():void
		{
			this.validate();
			this.stageWebView.historyForward();
		}

		/**
		 * @private
		 */
		override protected function initialize():void
		{
			this.createStageWebView();
		}

		/**
		 * @private
		 */
		override protected function draw():void
		{
			var dataInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_DATA);
			var sizeInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_SIZE);
			sizeInvalid = this.autoSizeIfNeeded() || sizeInvalid;
			if(sizeInvalid)
			{
				this.refreshViewPort();
			}
		}

		/**
		 * If the component's dimensions have not been set explicitly, it will
		 * measure its content and determine an ideal size for itself. If the
		 * <code>explicitWidth</code> or <code>explicitHeight</code> member
		 * variables are set, those value will be used without additional
		 * measurement. If one is set, but not the other, the dimension with the
		 * explicit value will not be measured, but the other non-explicit
		 * dimension will still need measurement.
		 *
		 * <p>Calls <code>setSizeInternal()</code> to set up the
		 * <code>actualWidth</code> and <code>actualHeight</code> member
		 * variables used for layout.</p>
		 *
		 * <p>Meant for internal use, and subclasses may override this function
		 * with a custom implementation.</p>
		 */
		protected function autoSizeIfNeeded():Boolean
		{
			var needsWidth:Boolean = this.explicitWidth !== this.explicitWidth; //isNaN
			var needsHeight:Boolean = this.explicitHeight !== this.explicitHeight; //isNaN
			if(!needsWidth && !needsHeight)
			{
				return false;
			}
			var newWidth:Number = this.explicitWidth;
			if(needsWidth)
			{
				newWidth = DEFAULT_SIZE;
			}
			var newHeight:Number = this.explicitWidth;
			if(needsHeight)
			{
				newHeight = DEFAULT_SIZE;
			}
			return this.setSizeInternal(newWidth, newHeight, false);
		}

		/**
		 * Creates the <code>StageWebView</code> instance.
		 *
		 * <p>Meant for internal use, and subclasses may override this function
		 * with a custom implementation.</p>
		 */
		protected function createStageWebView():void
		{
			if(isSupported)
			{
				this.stageWebView = new STAGE_WEB_VIEW_CLASS(this._useNative);
			}
			else
			{
				throw new IllegalOperationError(STAGE_WEB_VIEW_NOT_SUPPORTED_ERROR);
			}
			this.stageWebView.addEventListener(ErrorEvent.ERROR, stageWebView_errorHandler);
			//we're using the string here because this class is AIR-only
			this.stageWebView.addEventListener("locationChange", stageWebView_locationChangeHandler);
			this.stageWebView.addEventListener(flash.events.Event.COMPLETE, stageWebView_completeHandler);
		}

		/**
		 * @private
		 */
		protected function refreshViewPort():void
		{
			var starlingViewPort:Rectangle = Starling.current.viewPort;
			var stageWebViewViewPort:Rectangle = this.stageWebView.viewPort;
			if(!stageWebViewViewPort)
			{
				stageWebViewViewPort = new Rectangle();
			}

			HELPER_POINT.x = HELPER_POINT.y = 0;
			this.getTransformationMatrix(this.stage, HELPER_MATRIX);
			var globalScaleX:Number = matrixToScaleX(HELPER_MATRIX);
			var globalScaleY:Number = matrixToScaleY(HELPER_MATRIX);
			MatrixUtil.transformCoords(HELPER_MATRIX, 0, 0, HELPER_POINT);
			var nativeScaleFactor:Number = 1;
			if(Starling.current.supportHighResolutions)
			{
				nativeScaleFactor = Starling.current.nativeStage.contentsScaleFactor;
			}
			var scaleFactor:Number = Starling.contentScaleFactor / nativeScaleFactor;
			stageWebViewViewPort.x = Math.round(starlingViewPort.x + HELPER_POINT.x * scaleFactor);
			stageWebViewViewPort.y = Math.round(starlingViewPort.y + HELPER_POINT.y * scaleFactor);
			var viewPortWidth:Number = Math.round(this.actualWidth * scaleFactor * globalScaleX);
			if(viewPortWidth < 1 ||
				viewPortWidth !== viewPortWidth) //isNaN
			{
				viewPortWidth = 1;
			}
			var viewPortHeight:Number = Math.round(this.actualHeight * scaleFactor * globalScaleY);
			if(viewPortHeight < 1 ||
				viewPortHeight !== viewPortHeight) //isNaN
			{
				viewPortHeight = 1;
			}
			stageWebViewViewPort.width = viewPortWidth;
			stageWebViewViewPort.height = viewPortHeight;
			this.stageWebView.viewPort = stageWebViewViewPort;
		}

		/**
		 * @private
		 */
		protected function webView_addedToStageHandler(event:starling.events.Event):void
		{
			this.stageWebView.stage = Starling.current.nativeStage;
			this.addEventListener(starling.events.Event.ENTER_FRAME, webView_enterFrameHandler);
		}

		/**
		 * @private
		 */
		protected function webView_removedFromStageHandler(event:starling.events.Event):void
		{
			if(this.stageWebView)
			{
				this.stageWebView.stage = null;
			}
			this.removeEventListener(starling.events.Event.ENTER_FRAME, webView_enterFrameHandler);
		}

		/**
		 * @private
		 */
		protected function webView_enterFrameHandler(event:starling.events.Event):void
		{
			var target:DisplayObject = this;
			do
			{
				if(!target.hasVisibleArea)
				{
					this.stageWebView.stage = null;
					return;
				}
				target = target.parent;
			}
			while(target)
			this.stageWebView.stage = Starling.current.nativeStage;
		}

		/**
		 * @private
		 */
		protected function stageWebView_errorHandler(event:ErrorEvent):void
		{
			this.dispatchEventWith(FeathersEventType.ERROR, false, event);
		}

		/**
		 * @private
		 */
		protected function stageWebView_locationChangeHandler(event:flash.events.Event):void
		{
			this.dispatchEventWith(FeathersEventType.LOCATION_CHANGE);
		}
		
		protected function stageWebView_completeHandler(event:flash.events.Event):void
		{
			this.dispatchEventWith(starling.events.Event.COMPLETE);
		}
	}
}
