package _appId_.transitions
{
	import _appId_.view.core.IndexedAssetsScreen;
	import _appId_.view.core.IndexedScreen;

	import feathers.controls.ScreenNavigator;

	import starling.animation.Transitions;
	import starling.animation.Tween;
	import starling.core.Starling;
	import starling.display.DisplayObject;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 * TODO update this transition with last from feathers and make it compatible assetsScreen
	 */
	public class SlideTransitionManager
	{
		/**
		 * The <code>ScreenNavigator</code> being managed.
		 */
		protected var navigator : ScreenNavigator;

		/**
		 *
		 */
		protected var _activeTransition : Tween;

		/**
		 *
		 */
		protected var _savedOtherTarget : DisplayObject;

		/**
		 *
		 */
		protected var _savedCompleteHandler : Function;

		/**
		 * The duration of the transition, in seconds.
		 *
		 * @default 0.25
		 */
		public var duration : Number = 0.25;

		/**
		 * A delay before the transition starts, measured in seconds. This may
		 * be required on low-end systems that will slow down for a short time
		 * after heavy texture uploads.
		 *
		 * @default 0.1
		 */
		public var delay : Number = 0.1;

		/**
		 * The easing function to use.
		 *
		 * @default starling.animation.Transitions.EASE_OUT
		 */
		public var ease : Object = Transitions.EASE_OUT;

		/**
		 * Determines if the next transition should be skipped. After the
		 * transition, this value returns to <code>false</code>.
		 *
		 * @default false
		 */
		public var skipNextTransition : Boolean = false;

		/**
		 *
		 */
		public function SlideTransitionManager( navigator : ScreenNavigator )
		{
			if( !navigator )
			{
				throw new ArgumentError( "ScreenNavigator cannot be null." );
			}

			this.navigator = navigator;
			this.navigator.transition = this.onTransition;
		}

		/**
		 * The function passed to the <code>transition</code> property of the
		 * <code>ScreenNavigator</code>.
		 */
		protected function onTransition( oldScreen : DisplayObject , newScreen : DisplayObject , onComplete : Function ) : void
		{
			if( this._activeTransition )
			{
				this._savedOtherTarget = null;
				Starling.juggler.remove( this._activeTransition );
				this._activeTransition = null;
			}

			var assetsScreen : IndexedAssetsScreen;

			if( !oldScreen || !newScreen || this.skipNextTransition )
			{
				this.skipNextTransition = false;
				this._savedCompleteHandler = null;

				if( newScreen )
				{
					newScreen.x = 0;

					if( newScreen is IndexedScreen )
					{
						assetsScreen = newScreen as IndexedAssetsScreen;

						if( assetsScreen != null && !assetsScreen.isReady )
						{
							function runNewShow() : void
							{
								assetsScreen.show();

								assetsScreen.assetsComplete.remove( runTransition );
								assetsScreen.assetsError.remove( runTransition );
							}

							assetsScreen.assetsComplete.addOnce( runNewShow );
							assetsScreen.assetsError.addOnce( runNewShow );
						}
						else
						{
							( newScreen as IndexedScreen ).show();
						}
					}
				}
				if( oldScreen )
				{
					oldScreen.x = 0;
					if( oldScreen is IndexedScreen )
					{
						( oldScreen as IndexedScreen ).hide();
					}
				}
				if( onComplete != null )
				{
					onComplete();
				}
				return;
			}

			this._savedCompleteHandler = onComplete;

			var oldIndexedScreen : IndexedScreen = oldScreen as IndexedScreen;
			var newIndexedScreen : IndexedScreen = newScreen as IndexedScreen;

			var oldIndex : uint = oldIndexedScreen != null ? oldIndexedScreen.index : 0;
			var newIndex : uint = newIndexedScreen != null ? newIndexedScreen.index : 0;

			var activeTransition_onUpdate : Function;

			if( newIndex >= oldIndex )
			{
				oldScreen.x = 0;
				newScreen.x = this.navigator.width;
				activeTransition_onUpdate = this.activeTransitionPush_onUpdate;
			}
			else
			{
				oldScreen.x = 0;
				newScreen.x = -this.navigator.width;
				activeTransition_onUpdate = this.activeTransitionPop_onUpdate;
			}

			if( oldScreen != null ) oldScreen.touchable = false;

			this._savedOtherTarget = oldScreen;

			this._activeTransition = new Tween( newScreen , this.duration , this.ease );
			this._activeTransition.animate( "x" , 0 );
			this._activeTransition.delay = this.delay;
			this._activeTransition.onUpdate = activeTransition_onUpdate;
			this._activeTransition.onComplete = activeTransition_onComplete;

			function runTransition() : void
			{
				if( oldScreen is IndexedScreen ) ( oldScreen as IndexedScreen ).hide();
				if( newScreen is IndexedScreen ) ( newScreen as IndexedScreen ).show();

				if( assetsScreen != null )
				{
					assetsScreen.assetsComplete.remove( runTransition );
					assetsScreen.assetsError.remove( runTransition );
				}

				Starling.juggler.add( _activeTransition );
			}

			assetsScreen = newScreen as IndexedAssetsScreen;

			if( assetsScreen != null && !assetsScreen.isReady )
			{
				( newScreen as IndexedAssetsScreen ).assetsComplete.addOnce( runTransition );
				( newScreen as IndexedAssetsScreen ).assetsError.addOnce( runTransition );
			}
			else runTransition();
		}

		/**
		 *
		 */
		protected function activeTransitionPush_onUpdate() : void
		{
			if( this._savedOtherTarget )
			{
				var newScreen : DisplayObject = DisplayObject( this._activeTransition.target );
				this._savedOtherTarget.x = newScreen.x - this.navigator.width;
			}
		}

		/**
		 *
		 */
		protected function activeTransitionPop_onUpdate() : void
		{
			if( this._savedOtherTarget )
			{
				var newScreen : DisplayObject = DisplayObject( this._activeTransition.target );
				this._savedOtherTarget.x = newScreen.x + this.navigator.width;
			}
		}

		/**
		 *
		 */
		protected function activeTransition_onComplete() : void
		{
			this._activeTransition = null;
			this._savedOtherTarget = null;

			if( this._savedCompleteHandler != null )
			{
				this._savedCompleteHandler();
			}
		}
	}
}
