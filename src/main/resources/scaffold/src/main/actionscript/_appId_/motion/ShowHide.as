package _appId_.motion
{
	import _appId_.view.core.IndexAssetScreen;
	import _appId_.view.core.IndexScreen;

	import flash.utils.setTimeout;

	import starling.animation.Transitions;
	import starling.display.DisplayObject;

	/**
	 * @author SamYStudiO (contact@samystudio.net) on 05/12/2015.
	 */
	public class ShowHide
	{
		/**
		 *
		 */
		public static function createTransition( duration : Number = 0.5 , ease : Object = Transitions.EASE_OUT , waitNextScreenReady : Boolean = false , overlapDelay : Number = NaN ) : Function
		{
			return function ( oldScreen : DisplayObject , newScreen : DisplayObject , onComplete : Function ) : void
			{
				var oldIndexedScreen : IndexScreen = oldScreen as IndexScreen;
				var newIndexedScreen : IndexScreen = newScreen as IndexScreen;
				var newIndexedAssetScreen : IndexAssetScreen = newScreen as IndexAssetScreen;

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

				if( oldIndexedScreen != null ) hideOldScreen();
				else if( newIndexedScreen != null ) showNewScreen();
				else if( onComplete != null ) onComplete();
			}
		}

		/**
		 * @private
		 */
		public function ShowHide()
		{
			throw new Error( this + " cannot be instantiated" );
		}
	}
}
