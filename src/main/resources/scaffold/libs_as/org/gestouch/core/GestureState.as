package org.gestouch.core
{
	import flash.utils.Dictionary;
	import flash.errors.IllegalOperationError;

	/**
	 * @author Pavel fljot
	 */
	final public class GestureState
	{
		private static var allStatesInitialized : Boolean;

		private static function _initClass() : void
		{
			POSSIBLE.setValidNextStates( RECOGNIZED, BEGAN, FAILED );
			RECOGNIZED.setValidNextStates( POSSIBLE );
			BEGAN.setValidNextStates( CHANGED, ENDED, CANCELLED );
			CHANGED.setValidNextStates( CHANGED, ENDED, CANCELLED );
			ENDED.setValidNextStates( POSSIBLE );
			FAILED.setValidNextStates( POSSIBLE );
			CANCELLED.setValidNextStates( POSSIBLE );

			allStatesInitialized = true;
		}

		private var name : String;
		private var eventType : String;
		private var validTransitionStateMap : Dictionary = new Dictionary();

		public function GestureState( name : String, isEndState : Boolean = false )
		{
			if( allStatesInitialized )
			{
				throw new IllegalOperationError( "You cannot create gesture states." + "Use predefined constats like GestureState.RECOGNIZED" );
			}

			this.name = "GestureState." + name;
			this.eventType = "gesture" + name.charAt( 0 ).toUpperCase() + name.substr( 1 ).toLowerCase();
			this._isEndState = isEndState;
		}

		public function toString() : String
		{
			return name;
		}

		private function setValidNextStates( ...states ) : void
		{
			for each( var state : GestureState in states )
			{
				validTransitionStateMap[ state ] = true;
			}
		}

		public static const POSSIBLE : GestureState = new GestureState( "POSSIBLE" );
		public static const RECOGNIZED : GestureState = new GestureState( "RECOGNIZED", true );
		public static const BEGAN : GestureState = new GestureState( "BEGAN" );
		public static const CHANGED : GestureState = new GestureState( "CHANGED" );
		public static const ENDED : GestureState = new GestureState( "ENDED", true );
		public static const CANCELLED : GestureState = new GestureState( "CANCELLED", true );
		public static const FAILED : GestureState = new GestureState( "FAILED", true );

		{
			_initClass();
		}

		gestouch_internal function toEventType() : String
		{
			return eventType;
		}

		gestouch_internal function canTransitionTo( state : GestureState ) : Boolean
		{
			return (state in validTransitionStateMap);
		}

		private var _isEndState : Boolean = false;

		gestouch_internal function get isEndState() : Boolean
		{
			return _isEndState;
		}
	}
}
