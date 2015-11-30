package _appId_.view.core
{
	import _appId_.transitions.SlideTransitionManager;

	import feathers.controls.ScreenNavigator;
	import feathers.controls.ScreenNavigatorItem;
	import feathers.events.FeathersEventType;

	import flash.utils.Dictionary;

	import starling.display.DisplayObject;
	import starling.events.Event;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public class AAssetsNavigatorScrollScreen extends AAssetsScreen implements INavigatorScreen
	{
		/**
		 *
		 */
		protected var _navigator : ScreenNavigator;

		/**
		 *
		 */
		protected var _screenDefaultParams : Dictionary = new Dictionary( true );

		/**
		 *
		 */
		protected var _screenIndexes : Dictionary = new Dictionary( true );

		/**
		 *
		 */
		protected var _screens : Vector.<String> = new Vector.<String>();

		/**
		 * @inheritDoc
		 */
		public function get activeScreen() : DisplayObject
		{
			return _navigator.activeScreen;
		}

		/**
		 * @inheritDoc
		 */
		public function get activeScreenID() : String
		{
			return _navigator.activeScreenID;
		}

		/**
		 * @inheritDoc
		 */
		override public function get autoSizeMode() : String
		{
			return _navigator.autoSizeMode;
		}

		override public function set autoSizeMode( s : String ) : void
		{
			_navigator.autoSizeMode = s;
		}

		/**
		 * @inheritDoc
		 */
		override public function get clipContent() : Boolean
		{
			return super.clipContent;
		}

		override public function set clipContent( b : Boolean ) : void
		{
			super.clipContent = b;
			_navigator.clipContent = b;
		}

		/**
		 * @private
		 */
		protected var _defaultScreenID : String;

		/**
		 * @inheritDoc
		 */
		public function get defaultScreenID() : String
		{
			return _defaultScreenID;
		}

		/**
		 * @private
		 */
		protected var _defaultScreenParams : Object;

		/**
		 * @inheritDoc
		 */
		public function get defaultScreenParams() : Object
		{
			return _defaultScreenParams;
		}

		/**
		 * @inheritDoc
		 */
		public function get transition() : Function
		{
			return _navigator.transition;
		}

		public function set transition( f : Function ) : void
		{
			_navigator.transition = f;
		}

		/**
		 *
		 */
		public function AAssetsNavigatorScrollScreen()
		{
			super();

			_navigator = new ScreenNavigator();
			_navigator.addEventListener( Event.CHANGE , _bubbleEvent );
			_navigator.addEventListener( FeathersEventType.TRANSITION_START , _bubbleEvent );
			_navigator.addEventListener( FeathersEventType.TRANSITION_COMPLETE , _bubbleEvent );
			_navigator.addEventListener( FeathersEventType.CLEAR , _bubbleEvent );
			new SlideTransitionManager( _navigator );
			addChild( _navigator );
		}

		/**
		 * @inheritDoc
		 */
		override public function dispose() : void
		{
			super.dispose();

			_navigator.removeEventListeners();
		}

		/**
		 * @inheritDoc
		 */
		public function addScreen( id : String , item : ScreenNavigatorItem ) : void
		{
			addScreenAt( id , item , _navigator.getScreenIDs().length );
		}

		/**
		 * @inheritDoc
		 */
		public function addScreenAt( id : String , item : ScreenNavigatorItem , index : uint ) : void
		{
			var o : Object = item.properties == null ? {} : item.properties;
			o.index = index;
			item.properties = o;

			var screens : Vector.<String> = _navigator.getScreenIDs();

			for each( var screenID : String in screens )
			{
				var ni : ScreenNavigatorItem = _navigator.getScreen( screenID );
				if( ni.properties != null && !isNaN( ni.properties.index ) && ni.properties.index >= index ) ni.properties.index++;
			}

			_screenDefaultParams[ id ] = item.properties;
			_screenIndexes[ id ] = index;
			_screens[ index ] = id;

			if( _defaultScreenID == null && index == 0 ) _defaultScreenID = id;

			_navigator.addScreen( id , item );
		}

		/**
		 * @inheritDoc
		 */
		public function removeScreen( id : String ) : void
		{
			_navigator.removeScreen( id );

			var index : uint = _screenIndexes[ id ];

			var screens : Vector.<String> = _navigator.getScreenIDs();

			for each( var screenID : String in screens )
			{
				var ni : ScreenNavigatorItem = _navigator.getScreen( screenID );
				if( ni.properties != null && !isNaN( ni.properties.index ) && ni.properties.index >= index ) ni.properties.index--;
			}

			delete _screenIndexes[ id ];
			delete _screenDefaultParams[ id ];
			_screens.splice( index , 1 );
		}

		/**
		 * @inheritDoc
		 */
		public function removeScreenAt( index : uint ) : void
		{
			removeScreen( _screens[ index ] );
		}

		/**
		 * @inheritDoc
		 */
		public function removeAllScreens() : void
		{
			_navigator.removeAllScreens();

			_screenDefaultParams = new Dictionary( true );
			_screenIndexes = new Dictionary( true );
			_screens = new Vector.<String>();
			_defaultScreenID = null;
			_defaultScreenParams = null;
		}

		/**
		 * @inheritDoc
		 */
		public function clearScreen() : void
		{
			_navigator.clearScreen();
		}

		/**
		 * @inheritDoc
		 */
		public function getScreen( id : String ) : ScreenNavigatorItem
		{
			return _navigator.getScreen( id );
		}

		/**
		 * @inheritDoc
		 */
		public function getScreenAt( index : uint ) : ScreenNavigatorItem
		{
			return getScreen( _screens[ index ] );
		}

		/**
		 * @inheritDoc
		 */
		public function getScreenIDAt( index : uint ) : String
		{
			return _screens[ index ];
		}

		/**
		 * @inheritDoc
		 */
		public function getScreenIDs( result : Vector.<String> = null ) : Vector.<String>
		{
			return _navigator.getScreenIDs( result );
		}

		/**
		 * @inheritDoc
		 */
		public function getScreenDefaultParams( id : String ) : Object
		{
			return _navigator.hasScreen( id ) ? _screenDefaultParams[ id ] : null;
		}

		/**
		 * @inheritDoc
		 */
		public function setScreenDefaultParams( id : String , params : Object ) : void
		{
			if( _navigator.hasScreen( id ) )
			{
				_screenDefaultParams[ id ] = params;
				params.index = _screenIndexes[ id ];
			}
		}

		/**
		 * @inheritDoc
		 */
		public function hasScreen( id : String ) : Boolean
		{
			return _navigator.hasScreen( id );
		}

		/**
		 * @inheritDoc
		 */
		public function showScreen( screenID : String = null , additionalParams : Object = null ) : IndexedScreen
		{
			var screenIDIsNull : Boolean = screenID == null;

			screenID = screenIDIsNull ? _defaultScreenID : screenID;
			additionalParams = screenIDIsNull ? _defaultScreenParams : additionalParams;

			var screenItem : ScreenNavigatorItem = _navigator.getScreen( screenID );
			var o : Object = {};

			for( var prop : String in _screenDefaultParams[ screenID ] )
			{
				o[ prop ] = _screenDefaultParams[ screenID ][ prop ];
			}

			screenItem.properties = o;

			for( prop in additionalParams )
			{
				screenItem.properties[ prop ] = additionalParams[ prop ];
			}

			return _navigator.showScreen( screenID ) as IndexedScreen;
		}

		/**
		 *
		 */
		protected function _bubbleEvent( e : Event ) : void
		{
			dispatchEventWith( e.type );
		}
	}
}
