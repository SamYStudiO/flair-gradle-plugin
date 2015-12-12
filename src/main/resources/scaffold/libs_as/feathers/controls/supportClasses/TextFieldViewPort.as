/*
Feathers
Copyright 2012-2015 Bowler Hat LLC. All Rights Reserved.

This program is free software. You can redistribute and/or modify it in
accordance with the terms of the accompanying license agreement.
*/
package feathers.controls.supportClasses
{
	import feathers.core.FeathersControl;
	import feathers.utils.geom.matrixToRotation;
	import feathers.utils.geom.matrixToScaleX;
	import feathers.utils.geom.matrixToScaleY;

	import flash.display.Sprite;
	import flash.events.TextEvent;
	import flash.geom.Matrix;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	import flash.text.AntiAliasType;
	import flash.text.GridFitType;
	import flash.text.StyleSheet;
	import flash.text.TextField;
	import flash.text.TextFieldAutoSize;
	import flash.text.TextFormat;

	import starling.core.RenderSupport;
	import starling.core.Starling;
	import starling.display.DisplayObject;
	import starling.events.Event;
	import starling.utils.MatrixUtil;

	/**
	 * @private
	 */
	public class TextFieldViewPort extends FeathersControl implements IViewPort
	{
		private static const HELPER_MATRIX:Matrix = new Matrix();
		private static const HELPER_POINT:Point = new Point();

		public function TextFieldViewPort()
		{
			super();
			this.addEventListener(Event.ADDED_TO_STAGE, addedToStageHandler);
			this.addEventListener(Event.REMOVED_FROM_STAGE, removedFromStageHandler);
		}

		private var _textFieldContainer:Sprite;
		private var _textField:TextField;

		/**
		 * @private
		 */
		private var _text:String = "";

		/**
		 * @see feathers.controls.ScrollText#text
		 */
		public function get text():String
		{
			return this._text;
		}

		/**
		 * @private
		 */
		public function set text(value:String):void
		{
			if(!value)
			{
				value = "";
			}
			if(this._text == value)
			{
				return;
			}
			this._text = value;
			this.invalidate(INVALIDATION_FLAG_DATA);
		}

		/**
		 * @private
		 */
		private var _isHTML:Boolean = false;

		/**
		 * @see feathers.controls.ScrollText#isHTML
		 */
		public function get isHTML():Boolean
		{
			return this._isHTML;
		}

		/**
		 * @private
		 */
		public function set isHTML(value:Boolean):void
		{
			if(this._isHTML == value)
			{
				return;
			}
			this._isHTML = value;
			this.invalidate(INVALIDATION_FLAG_DATA);
		}

		/**
		 * @private
		 */
		private var _textFormat:TextFormat;

		/**
		 * @see feathers.controls.ScrollText#textFormat
		 */
		public function get textFormat():TextFormat
		{
			return this._textFormat;
		}

		/**
		 * @private
		 */
		public function set textFormat(value:TextFormat):void
		{
			if(this._textFormat == value)
			{
				return;
			}
			this._textFormat = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _disabledTextFormat:TextFormat;

		/**
		 * @see feathers.controls.ScrollText#disabledTextFormat
		 */
		public function get disabledTextFormat():TextFormat
		{
			return this._disabledTextFormat;
		}

		/**
		 * @private
		 */
		public function set disabledTextFormat(value:TextFormat):void
		{
			if(this._disabledTextFormat == value)
			{
				return;
			}
			this._disabledTextFormat = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		protected var _styleSheet:StyleSheet;

		/**
		 * @see feathers.controls.ScrollText#styleSheet
		 */
		public function get styleSheet():StyleSheet
		{
			return this._styleSheet;
		}

		/**
		 * @private
		 */
		public function set styleSheet(value:StyleSheet):void
		{
			if(this._styleSheet == value)
			{
				return;
			}
			this._styleSheet = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _embedFonts:Boolean = false;

		/**
		 * @see feathers.controls.ScrollText#embedFonts
		 */
		public function get embedFonts():Boolean
		{
			return this._embedFonts;
		}

		/**
		 * @private
		 */
		public function set embedFonts(value:Boolean):void
		{
			if(this._embedFonts == value)
			{
				return;
			}
			this._embedFonts = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _antiAliasType:String = AntiAliasType.ADVANCED;

		/**
		 * @see feathers.controls.ScrollText#antiAliasType
		 */
		public function get antiAliasType():String
		{
			return this._antiAliasType;
		}

		/**
		 * @private
		 */
		public function set antiAliasType(value:String):void
		{
			if(this._antiAliasType == value)
			{
				return;
			}
			this._antiAliasType = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _background:Boolean = false;

		/**
		 * @see feathers.controls.ScrollText#background
		 */
		public function get background():Boolean
		{
			return this._background;
		}

		/**
		 * @private
		 */
		public function set background(value:Boolean):void
		{
			if(this._background == value)
			{
				return;
			}
			this._background = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _backgroundColor:uint = 0xffffff;

		/**
		 * @see feathers.controls.ScrollText#backgroundColor
		 */
		public function get backgroundColor():uint
		{
			return this._backgroundColor;
		}

		/**
		 * @private
		 */
		public function set backgroundColor(value:uint):void
		{
			if(this._backgroundColor == value)
			{
				return;
			}
			this._backgroundColor = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _border:Boolean = false;

		/**
		 * @see feathers.controls.ScrollText#border
		 */
		public function get border():Boolean
		{
			return this._border;
		}

		/**
		 * @private
		 */
		public function set border(value:Boolean):void
		{
			if(this._border == value)
			{
				return;
			}
			this._border = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _borderColor:uint = 0x000000;

		/**
		 * @see feathers.controls.ScrollText#borderColor
		 */
		public function get borderColor():uint
		{
			return this._borderColor;
		}

		/**
		 * @private
		 */
		public function set borderColor(value:uint):void
		{
			if(this._borderColor == value)
			{
				return;
			}
			this._borderColor = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _cacheAsBitmap:Boolean = true;

		/**
		 * @see feathers.controls.ScrollText#cacheAsBitmap
		 */
		public function get cacheAsBitmap():Boolean
		{
			return this._cacheAsBitmap;
		}

		/**
		 * @private
		 */
		public function set cacheAsBitmap(value:Boolean):void
		{
			if(this._cacheAsBitmap == value)
			{
				return;
			}
			this._cacheAsBitmap = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _condenseWhite:Boolean = false;

		/**
		 * @see feathers.controls.ScrollText#condenseWhite
		 */
		public function get condenseWhite():Boolean
		{
			return this._condenseWhite;
		}

		/**
		 * @private
		 */
		public function set condenseWhite(value:Boolean):void
		{
			if(this._condenseWhite == value)
			{
				return;
			}
			this._condenseWhite = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _displayAsPassword:Boolean = false;

		/**
		 * @see feathers.controls.ScrollText#displayAsPassword
		 */
		public function get displayAsPassword():Boolean
		{
			return this._displayAsPassword;
		}

		/**
		 * @private
		 */
		public function set displayAsPassword(value:Boolean):void
		{
			if(this._displayAsPassword == value)
			{
				return;
			}
			this._displayAsPassword = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _gridFitType:String = GridFitType.PIXEL;

		/**
		 * @see feathers.controls.ScrollText#gridFitType
		 */
		public function get gridFitType():String
		{
			return this._gridFitType;
		}

		/**
		 * @private
		 */
		public function set gridFitType(value:String):void
		{
			if(this._gridFitType == value)
			{
				return;
			}
			this._gridFitType = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		/**
		 * @private
		 */
		private var _sharpness:Number = 0;

		/**
		 * @see feathers.controls.ScrollText#sharpness
		 */
		public function get sharpness():Number
		{
			return this._sharpness;
		}

		/**
		 * @private
		 */
		public function set sharpness(value:Number):void
		{
			if(this._sharpness == value)
			{
				return;
			}
			this._sharpness = value;
			this.invalidate(INVALIDATION_FLAG_DATA);
		}

		/**
		 * @private
		 */
		private var _thickness:Number = 0;

		/**
		 * @see feathers.controls.ScrollText#thickness
		 */
		public function get thickness():Number
		{
			return this._thickness;
		}

		/**
		 * @private
		 */
		public function set thickness(value:Number):void
		{
			if(this._thickness == value)
			{
				return;
			}
			this._thickness = value;
			this.invalidate(INVALIDATION_FLAG_DATA);
		}

		private var _minVisibleWidth:Number = 0;

		public function get minVisibleWidth():Number
		{
			return this._minVisibleWidth;
		}

		public function set minVisibleWidth(value:Number):void
		{
			if(this._minVisibleWidth == value)
			{
				return;
			}
			if(value !== value) //isNaN
			{
				throw new ArgumentError("minVisibleWidth cannot be NaN");
			}
			this._minVisibleWidth = value;
			this.invalidate(INVALIDATION_FLAG_SIZE);
		}

		private var _maxVisibleWidth:Number = Number.POSITIVE_INFINITY;

		public function get maxVisibleWidth():Number
		{
			return this._maxVisibleWidth;
		}

		public function set maxVisibleWidth(value:Number):void
		{
			if(this._maxVisibleWidth == value)
			{
				return;
			}
			if(value !== value) //isNaN
			{
				throw new ArgumentError("maxVisibleWidth cannot be NaN");
			}
			this._maxVisibleWidth = value;
			this.invalidate(INVALIDATION_FLAG_SIZE);
		}

		private var _actualVisibleWidth:Number = 0;

		private var _explicitVisibleWidth:Number = NaN;

		public function get visibleWidth():Number
		{
			if(this._explicitVisibleWidth !== this._explicitVisibleWidth) //isNaN
			{
				return this._actualVisibleWidth;
			}
			return this._explicitVisibleWidth;
		}

		public function set visibleWidth(value:Number):void
		{
			if(this._explicitVisibleWidth == value ||
				(value !== value && this._explicitVisibleWidth !== this._explicitVisibleWidth)) //isNaN
			{
				return;
			}
			this._explicitVisibleWidth = value;
			this.invalidate(INVALIDATION_FLAG_SIZE);
		}

		private var _minVisibleHeight:Number = 0;

		public function get minVisibleHeight():Number
		{
			return this._minVisibleHeight;
		}

		public function set minVisibleHeight(value:Number):void
		{
			if(this._minVisibleHeight == value)
			{
				return;
			}
			if(value !== value) //isNaN
			{
				throw new ArgumentError("minVisibleHeight cannot be NaN");
			}
			this._minVisibleHeight = value;
			this.invalidate(INVALIDATION_FLAG_SIZE);
		}

		private var _maxVisibleHeight:Number = Number.POSITIVE_INFINITY;

		public function get maxVisibleHeight():Number
		{
			return this._maxVisibleHeight;
		}

		public function set maxVisibleHeight(value:Number):void
		{
			if(this._maxVisibleHeight == value)
			{
				return;
			}
			if(value !== value) //isNaN
			{
				throw new ArgumentError("maxVisibleHeight cannot be NaN");
			}
			this._maxVisibleHeight = value;
			this.invalidate(INVALIDATION_FLAG_SIZE);
		}

		private var _actualVisibleHeight:Number = 0;

		private var _explicitVisibleHeight:Number = NaN;

		public function get visibleHeight():Number
		{
			if(this._explicitVisibleHeight !== this._explicitVisibleHeight) //isNaN
			{
				return this._actualVisibleHeight;
			}
			return this._explicitVisibleHeight;
		}

		public function set visibleHeight(value:Number):void
		{
			if(this._explicitVisibleHeight == value ||
				(value !== value && this._explicitVisibleHeight !== this._explicitVisibleHeight)) //isNaN
			{
				return;
			}
			this._explicitVisibleHeight = value;
			this.invalidate(INVALIDATION_FLAG_SIZE);
		}

		public function get contentX():Number
		{
			return 0;
		}

		public function get contentY():Number
		{
			return 0;
		}

		private var _scrollStep:Number;

		public function get horizontalScrollStep():Number
		{
			return this._scrollStep;
		}

		public function get verticalScrollStep():Number
		{
			return this._scrollStep;
		}

		private var _horizontalScrollPosition:Number = 0;

		public function get horizontalScrollPosition():Number
		{
			return this._horizontalScrollPosition;
		}

		public function set horizontalScrollPosition(value:Number):void
		{
			if(this._horizontalScrollPosition == value)
			{
				return;
			}
			this._horizontalScrollPosition = value;
			this.invalidate(INVALIDATION_FLAG_SCROLL);
		}

		private var _verticalScrollPosition:Number = 0;

		public function get verticalScrollPosition():Number
		{
			return this._verticalScrollPosition;
		}

		public function set verticalScrollPosition(value:Number):void
		{
			if(this._verticalScrollPosition == value)
			{
				return;
			}
			this._verticalScrollPosition = value;
			this.invalidate(INVALIDATION_FLAG_SCROLL);
		}

		private var _paddingTop:Number = 0;

		public function get paddingTop():Number
		{
			return this._paddingTop;
		}

		public function set paddingTop(value:Number):void
		{
			if(this._paddingTop == value)
			{
				return;
			}
			this._paddingTop = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		private var _paddingRight:Number = 0;

		public function get paddingRight():Number
		{
			return this._paddingRight;
		}

		public function set paddingRight(value:Number):void
		{
			if(this._paddingRight == value)
			{
				return;
			}
			this._paddingRight = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		private var _paddingBottom:Number = 0;

		public function get paddingBottom():Number
		{
			return this._paddingBottom;
		}

		public function set paddingBottom(value:Number):void
		{
			if(this._paddingBottom == value)
			{
				return;
			}
			this._paddingBottom = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		private var _paddingLeft:Number = 0;

		public function get paddingLeft():Number
		{
			return this._paddingLeft;
		}

		public function set paddingLeft(value:Number):void
		{
			if(this._paddingLeft == value)
			{
				return;
			}
			this._paddingLeft = value;
			this.invalidate(INVALIDATION_FLAG_STYLES);
		}

		override public function render(support:RenderSupport, parentAlpha:Number):void
		{
			var starlingViewPort:Rectangle = Starling.current.viewPort;
			HELPER_POINT.x = HELPER_POINT.y = 0;
			this.parent.getTransformationMatrix(this.stage, HELPER_MATRIX);
			MatrixUtil.transformCoords(HELPER_MATRIX, 0, 0, HELPER_POINT);
			var nativeScaleFactor:Number = 1;
			if(Starling.current.supportHighResolutions)
			{
				nativeScaleFactor = Starling.current.nativeStage.contentsScaleFactor;
			}
			var scaleFactor:Number = Starling.contentScaleFactor / nativeScaleFactor;
			this._textFieldContainer.x = starlingViewPort.x + HELPER_POINT.x * scaleFactor;
			this._textFieldContainer.y = starlingViewPort.y + HELPER_POINT.y * scaleFactor;
			this._textFieldContainer.scaleX = matrixToScaleX(HELPER_MATRIX) * scaleFactor;
			this._textFieldContainer.scaleY = matrixToScaleY(HELPER_MATRIX) * scaleFactor;
			this._textFieldContainer.rotation = matrixToRotation(HELPER_MATRIX) * 180 / Math.PI;
			this._textFieldContainer.alpha = parentAlpha * this.alpha;
			super.render(support, parentAlpha);
		}

		override protected function initialize():void
		{
			this._textFieldContainer = new Sprite();
			this._textFieldContainer.visible = false;
			this._textField = new TextField();
			this._textField.autoSize = TextFieldAutoSize.LEFT;
			this._textField.selectable = false;
			this._textField.mouseWheelEnabled = false;
			this._textField.wordWrap = true;
			this._textField.multiline = true;
			this._textField.addEventListener(TextEvent.LINK, textField_linkHandler);
			this._textFieldContainer.addChild(this._textField);
		}

		override protected function draw():void
		{
			var dataInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_DATA);
			var sizeInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_SIZE);
			var scrollInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_SCROLL);
			var stylesInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_STYLES);
			var stateInvalid:Boolean = this.isInvalid(INVALIDATION_FLAG_STATE);

			if(stylesInvalid)
			{
				this._textField.antiAliasType = this._antiAliasType;
				this._textField.background = this._background;
				this._textField.backgroundColor = this._backgroundColor;
				this._textField.border = this._border;
				this._textField.borderColor = this._borderColor;
				this._textField.condenseWhite = this._condenseWhite;
				this._textField.displayAsPassword = this._displayAsPassword;
				this._textField.embedFonts = this._embedFonts;
				this._textField.gridFitType = this._gridFitType;
				this._textField.sharpness = this._sharpness;
				this._textField.thickness = this._thickness;
				this._textField.cacheAsBitmap = this._cacheAsBitmap;
				this._textField.x = this._paddingLeft;
				this._textField.y = this._paddingTop;
			}

			if(dataInvalid || stylesInvalid || stateInvalid)
			{
				if(this._styleSheet)
				{
					this._textField.styleSheet = this._styleSheet;
				}
				else
				{
					this._textField.styleSheet = null;
					if(!this._isEnabled && this._disabledTextFormat)
					{
						this._textField.defaultTextFormat = this._disabledTextFormat;
					}
					else if(this._textFormat)
					{
						this._textField.defaultTextFormat = this._textFormat;
					}
				}
				if(this._isHTML)
				{
					this._textField.htmlText = this._text;
				}
				else
				{
					this._textField.text = this._text;
				}
				this._scrollStep = this._textField.getLineMetrics(0).height * Starling.contentScaleFactor;
			}

			var calculatedVisibleWidth:Number = this._explicitVisibleWidth;
			if(calculatedVisibleWidth != calculatedVisibleWidth)
			{
				if(this.stage)
				{
					calculatedVisibleWidth = this.stage.stageWidth;
				}
				else
				{
					calculatedVisibleWidth = Starling.current.stage.stageWidth;
				}
				if(calculatedVisibleWidth < this._minVisibleWidth)
				{
					calculatedVisibleWidth = this._minVisibleWidth;
				}
				else if(calculatedVisibleWidth > this._maxVisibleWidth)
				{
					calculatedVisibleWidth = this._maxVisibleWidth;
				}
			}
			this._textField.width = calculatedVisibleWidth - this._paddingLeft - this._paddingRight;
			var totalContentHeight:Number = this._textField.height + this._paddingTop + this._paddingBottom;
			var calculatedVisibleHeight:Number = this._explicitVisibleHeight;
			if(calculatedVisibleHeight != calculatedVisibleHeight)
			{
				calculatedVisibleHeight = totalContentHeight;
				if(calculatedVisibleHeight < this._minVisibleHeight)
				{
					calculatedVisibleHeight = this._minVisibleHeight;
				}
				else if(calculatedVisibleHeight > this._maxVisibleHeight)
				{
					calculatedVisibleHeight = this._maxVisibleHeight;
				}
			}
			sizeInvalid = this.setSizeInternal(calculatedVisibleWidth, totalContentHeight, false) || sizeInvalid;
			this._actualVisibleWidth = calculatedVisibleWidth;
			this._actualVisibleHeight = calculatedVisibleHeight;

			if(sizeInvalid || scrollInvalid)
			{
				var scrollRect:Rectangle = this._textFieldContainer.scrollRect;
				if(!scrollRect)
				{
					scrollRect = new Rectangle();
				}
				scrollRect.width = calculatedVisibleWidth;
				scrollRect.height = calculatedVisibleHeight;
				scrollRect.x = this._horizontalScrollPosition;
				scrollRect.y = this._verticalScrollPosition;
				this._textFieldContainer.scrollRect = scrollRect;
			}
		}

		private function addedToStageHandler(event:Event):void
		{
			Starling.current.nativeStage.addChild(this._textFieldContainer);
			this.addEventListener(Event.ENTER_FRAME, enterFrameHandler);
		}

		private function removedFromStageHandler(event:Event):void
		{
			Starling.current.nativeStage.removeChild(this._textFieldContainer);
			this.removeEventListener(Event.ENTER_FRAME, enterFrameHandler);
		}

		private function enterFrameHandler(event:Event):void
		{
			var target:DisplayObject = this;
			do
			{
				if(!target.hasVisibleArea)
				{
					this._textFieldContainer.visible = false;
					return;
				}
				target = target.parent;
			}
			while(target)
			this._textFieldContainer.visible = true;
		}

		protected function textField_linkHandler(event:TextEvent):void
		{
			this.dispatchEventWith(Event.TRIGGERED, false, event.text);
		}
	}
}
