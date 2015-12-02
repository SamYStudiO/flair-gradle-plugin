package _appId_.view.transitions
{
	import _appId_.view.core.IndexedAssetsScreen;
	import _appId_.view.core.IndexedScreen;

	import feathers.controls.ScreenNavigator;

	import flash.utils.setTimeout;

	import starling.display.DisplayObject;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class ShowHideTransitionManager
	{
		/**
		 * The <code>ScreenNavigator</code> being managed.
		 */
		protected var _navigator : ScreenNavigator;

		/**
		 *
		 */
		public var overlapDelay : Number;

		/**
		 *
		 */
		public var waitNextScreenReady : Boolean;

		/**
		 * A delay before the transition starts, measured in seconds. This may
		 * be required on low-end systems that will slow down for a short time
		 * after heavy texture uploads.
		 *
		 * @default 0.1
		 */
		public var delay : Number = 0.1;

		/**
		 *
		 */
		public function ShowHideTransitionManager( navigator : ScreenNavigator )
		{
			if( !navigator )
			{
				throw new ArgumentError( "ScreenNavigator cannot be null." );
			}

			_navigator = navigator;
			_navigator.transition = onTransition;
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

			function hideOldScreen() : void
			{
				if( waitNextScreenReady && newIndexedAssetScreen != null && !newIndexedAssetScreen.isReady )
				{
					newIndexedAssetScreen.assetsComplete.addOnce( hideOldScreen );
				}
				else
				{
					if( newIndexedScreen != null )
					{
						if( !isNaN( overlapDelay ) && overlapDelay >= 0 )
						{
							if( overlapDelay == 0 ) showNewScreen();
							else setTimeout( showNewScreen , overlapDelay * 1000 )
						}
						else oldIndexedScreen.hidden.addOnce( showNewScreen );
					}
					else oldIndexedScreen.hidden.addOnce( onComplete );

					oldIndexedScreen.hide();
				}
			}

			function showNewScreen() : void
			{
				if( newIndexedAssetScreen != null && !newIndexedAssetScreen.isReady )
				{
					newIndexedAssetScreen.assetsComplete.addOnce( showNewScreen );
				}
				else
				{
					if( onComplete != null ) newIndexedScreen.shown.addOnce( onComplete );
					newIndexedScreen.show();
				}
			}

			if( oldIndexedScreen != null )
			{
				if( !isNaN( delay ) && delay >= 0 ) setTimeout( hideOldScreen , delay * 1000 );
				else hideOldScreen();
			}
			else if( newIndexedScreen != null )
			{
				if( !isNaN( delay ) && delay >= 0 ) setTimeout( showNewScreen , delay * 1000 );
				else showNewScreen();
			}
			else if( onComplete != null ) onComplete();
		}
	}
}
