package _appId_.motion
{
	import _appId_.view.core.IShowHideScreen;

	import feathers.events.FeathersEventType;

	import flash.utils.setTimeout;

	import starling.display.DisplayObject;
	import starling.events.Event;

	/**
	 * @author SamYStudiO (contact@samystudio.net) on 05/12/2015.
	 */
	public class ShowHide
	{
		/**
		 *
		 */
		public static function createTransition( waitNewScreenInitialized : Boolean = false , overlapDelay : Number = NaN ) : Function
		{
			return function ( oldScreen : DisplayObject , newScreen : DisplayObject , onComplete : Function ) : void
			{
				var oldShowHideScreen : IShowHideScreen = oldScreen as IShowHideScreen;
				var newShowHideScreen : IShowHideScreen = newScreen as IShowHideScreen;

				function hideOldScreen() : void
				{
					if( waitNewScreenInitialized && newShowHideScreen && !newShowHideScreen.isInitialized )
					{
						newShowHideScreen.addEventListener( FeathersEventType.INITIALIZE , function ( e : Event ) : void
						{
							newShowHideScreen.removeEventListener( FeathersEventType.INITIALIZE , arguments.callee );
							hideOldScreen();
						} );
					}
					else
					{
						if( newShowHideScreen )
						{
							if( !isNaN( overlapDelay ) && overlapDelay >= 0 )
							{
								if( overlapDelay == 0 ) showNewScreen();
								else setTimeout( showNewScreen , overlapDelay * 1000 )
							}
							else oldShowHideScreen.hidden.addOnce( showNewScreen );
						}
						else oldShowHideScreen.hidden.addOnce( onComplete );

						oldShowHideScreen.hide();
					}
				}

				function showNewScreen() : void
				{
					if( newShowHideScreen && !newShowHideScreen.isInitialized )
					{
						newShowHideScreen.addEventListener( FeathersEventType.INITIALIZE , function ( e : Event ) : void
						{
							newShowHideScreen.removeEventListener( FeathersEventType.INITIALIZE , arguments.callee );
							showNewScreen();
						} );
					}
					else
					{
						if( onComplete ) newShowHideScreen.shown.addOnce( onComplete );
						newShowHideScreen.show();
					}
				}

				if( oldShowHideScreen ) hideOldScreen();
				else if( newShowHideScreen ) showNewScreen();
				else if( onComplete ) onComplete();
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
