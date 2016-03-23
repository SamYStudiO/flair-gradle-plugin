/*
Feathers
Copyright 2012-2016 Bowler Hat LLC. All Rights Reserved.

This program is free software. You can redistribute and/or modify it in
accordance with the terms of the accompanying license agreement.
*/
package feathers.motion
{
	import starling.animation.Transitions;
	import starling.display.DisplayObject;
	import starling.display.DisplayObjectContainer;

	/**
	 * Creates animated effects, like transitions for screen navigators, that
	 * animates the `alpha` property of a display object to fade it in or out.
	 *
	 * @see ../../../help/transitions.html#fade Transitions for Feathers screen navigators: Fade
	 */
	public class Fade
	{
		/**
		 * @private
		 */
		protected static const SCREEN_REQUIRED_ERROR:String = "Cannot transition if both old screen and new screen are null.";

		/**
		 * Creates a transition function for a screen navigator that fades in
		 * the new screen by animating the `alpha` property from `0.0` to `1.0`,
		 * while the old screen remains fully opaque at a lower depth.
		 *
		 * @see ../../../help/transitions.html#fade Transitions for Feathers screen navigators: Fade
		 * @see feathers.controls.StackScreenNavigator#pushTransition
		 * @see feathers.controls.StackScreenNavigator#popTransition
		 * @see feathers.controls.ScreenNavigator#transition
		 */
		public static function createFadeInTransition(duration:Number = 0.5, ease:Object = Transitions.EASE_OUT, tweenProperties:Object = null):Function
		{
			return function(oldScreen:DisplayObject, newScreen:DisplayObject, onComplete:Function):void
			{
				if(!oldScreen && !newScreen)
				{
					throw new ArgumentError(SCREEN_REQUIRED_ERROR);
				}
				if(newScreen)
				{
					newScreen.alpha = 0;
					//make sure the new screen is on top
					var parent:DisplayObjectContainer = newScreen.parent;
					parent.setChildIndex(newScreen, parent.numChildren - 1);
					if(oldScreen) //oldScreen can be null, that's okay
					{
						oldScreen.alpha = 1;
					}
					new FadeTween(newScreen, oldScreen, duration, ease, onComplete, tweenProperties);
				}
				else
				{
					//there's no new screen to fade in, but we still want some
					//kind of animation, so we'll just fade out the old screen
					//in order to have some animation, we're going to fade out
					oldScreen.alpha = 1;
					new FadeTween(oldScreen, null, duration, ease, onComplete, tweenProperties);
				}
			}
		}

		/**
		 * Creates a transition function for a screen navigator that fades out
		 * the old screen by animating the `alpha` property from `1.0` to `0.0`,
		 * while the new screen remains fully opaque at a lower depth.
		 *
		 * @see ../../../help/transitions.html#fade Transitions for Feathers screen navigators: Fade
		 * @see feathers.controls.StackScreenNavigator#pushTransition
		 * @see feathers.controls.StackScreenNavigator#popTransition
		 * @see feathers.controls.ScreenNavigator#transition
		 */
		public static function createFadeOutTransition(duration:Number = 0.5, ease:Object = Transitions.EASE_OUT, tweenProperties:Object = null):Function
		{
			return function(oldScreen:DisplayObject, newScreen:DisplayObject, onComplete:Function):void
			{
				if(!oldScreen && !newScreen)
				{
					throw new ArgumentError(SCREEN_REQUIRED_ERROR);
				}
				if(oldScreen)
				{
					//make sure the old screen is on top
					var parent:DisplayObjectContainer = oldScreen.parent;
					parent.setChildIndex(oldScreen, parent.numChildren - 1);
					oldScreen.alpha = 1;
					if(newScreen) //newScreen can be null, that's okay
					{
						newScreen.alpha = 1;
					}
					new FadeTween(oldScreen, null, duration, ease, onComplete, tweenProperties);
				}
				else
				{
					//there's no old screen to fade out, but we still want some
					//kind of animation, so we'll just fade in the new screen
					//in order to have some animation, we're going to fade out
					newScreen.alpha = 0;
					new FadeTween(newScreen, null, duration, ease, onComplete, tweenProperties);
				}
			}
		}

		/**
		 * Creates a transition function for a screen navigator that crossfades
		 * the screens. In other words, the old screen fades out, animating the
		 * `alpha` property from `1.0` to `0.0`. Simultaneously, the new screen
		 * fades in, animating its `alpha` property from `0.0` to `1.0`.
		 *
		 * @see ../../../help/transitions.html#fade Transitions for Feathers screen navigators: Fade
		 * @see feathers.controls.StackScreenNavigator#pushTransition
		 * @see feathers.controls.StackScreenNavigator#popTransition
		 * @see feathers.controls.ScreenNavigator#transition
		 */
		public static function createCrossfadeTransition(duration:Number = 0.5, ease:Object = Transitions.EASE_OUT, tweenProperties:Object = null):Function
		{
			return function(oldScreen:DisplayObject, newScreen:DisplayObject, onComplete:Function):void
			{
				if(!oldScreen && !newScreen)
				{
					throw new ArgumentError(SCREEN_REQUIRED_ERROR);
				}
				if(newScreen)
				{
					newScreen.alpha = 0;
					if(oldScreen) //oldScreen can be null, that's okay
					{
						oldScreen.alpha = 1;
					}
					new FadeTween(newScreen, oldScreen, duration, ease, onComplete, tweenProperties);
				}
				else //we only have the old screen
				{
					oldScreen.alpha = 1;
					new FadeTween(oldScreen, null, duration, ease, onComplete, tweenProperties);
				}
			}
		}
	}
}

import starling.animation.Tween;
import starling.core.Starling;
import starling.display.DisplayObject;

class FadeTween extends Tween
{
	public function FadeTween(target:DisplayObject, otherTarget:DisplayObject,
		duration:Number, ease:Object, onCompleteCallback:Function,
		tweenProperties:Object)
	{
		super(target, duration, ease);
		if(target.alpha == 0)
		{
			this.fadeTo(1);
		}
		else
		{
			this.fadeTo(0);
		}
		if(tweenProperties)
		{
			for(var propertyName:String in tweenProperties)
			{
				this[propertyName] = tweenProperties[propertyName];
			}
		}
		if(otherTarget)
		{
			this._otherTarget = otherTarget;
			this.onUpdate = this.updateOtherTarget;
		}
		this._onCompleteCallback = onCompleteCallback;
		this.onComplete = this.cleanupTween;
		Starling.juggler.add(this);
	}

	private var _otherTarget:DisplayObject;
	private var _onCompleteCallback:Function;

	private function updateOtherTarget():void
	{
		var newScreen:DisplayObject = DisplayObject(this.target);
		this._otherTarget.alpha = 1 - newScreen.alpha;
	}

	private function cleanupTween():void
	{
		this.target.alpha = 1;
		if(this._otherTarget)
		{
			this._otherTarget.alpha = 1;
		}
		if(this._onCompleteCallback !== null)
		{
			this._onCompleteCallback();
		}
	}
}