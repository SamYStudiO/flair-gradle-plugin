package _appId_.transitions
{
	import _appId_.view.core.IndexedAssetsScreen;
	import _appId_.view.core.IndexedScreen;

	import feathers.controls.ScreenNavigator;
	import feathers.motion.Slide;

	import starling.animation.Transitions;
	import starling.display.DisplayObject;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class SlideTransitionManager
	{
		/**
		 * The <code>ScreenNavigator</code> being managed.
		 */
		protected var _navigator : ScreenNavigator;

		/**
		 * @private
		 */
		protected var _pushTransition : Function;

		/**
		 * @private
		 */
		protected var _popTransition : Function;

		/**
		 *
		 */
		public var waitNextScreenReady : Boolean;

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
		 *
		 */
		public function SlideTransitionManager( navigator : ScreenNavigator )
		{
			if( !navigator )
			{
				throw new ArgumentError( "ScreenNavigator cannot be null." );
			}

			this._navigator = navigator;
			this._navigator.transition = this.onTransition;
		}

		/**
		 * The function passed to the <code>transition</code> property of the
		 * <code>ScreenNavigator</code>.
		 */
		protected function onTransition( oldScreen : DisplayObject , newScreen : DisplayObject , onComplete : Function ) : void
		{
			var oldIndexedScreen : IndexedScreen = oldScreen as IndexedScreen;
			var newIndexedScreen : IndexedScreen = newScreen as IndexedScreen;
			var newIndexedAssetScreen : IndexedAssetsScreen = newScreen as IndexedAssetsScreen;

			function doTransition() : void
			{
				if( waitNextScreenReady && newIndexedAssetScreen != null && !newIndexedAssetScreen.isReady )
				{
					newIndexedAssetScreen.assetsComplete.addOnce( doTransition );
				}
				else
				{
					var oldIndex : uint = oldIndexedScreen != null ? oldIndexedScreen.index : 0;
					var newIndex : uint = newIndexedScreen != null ? newIndexedScreen.index : 0;

					if( newIndex >= oldIndex )
					{
						if( _pushTransition == null ) _pushTransition = Slide.createSlideLeftTransition( duration , ease , {delay : delay} );

						this._pushTransition( oldScreen , newScreen , onComplete );
					}

					else
					{
						if( _popTransition == null ) _popTransition = Slide.createSlideRightTransition( duration , ease , {delay : delay} );

						_popTransition( oldScreen , newScreen , onComplete );
					}
				}
			}

			doTransition();
		}
	}
}
