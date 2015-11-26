package _appId_.utils
{
	import _appId_.actors.STARLING_MAIN;
	import _appId_.utils.object.merge;
	import _appId_.utils.string.trim;
	import _appId_.view.INavigatorScreen;
	import _appId_.view.IndexedScreen;

	import feathers.events.FeathersEventType;

	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import flash.utils.setTimeout;

	import org.osflash.signals.Signal;

	/**
	 * @author SamYStudiO
	 */
	public final class NavigatorManager
	{
		/**
		 *
		 */
		private static var __instance : NavigatorManager;

		/**
		 *
		 */
		public static function getInstance() : NavigatorManager
		{
			if( __instance == null ) __instance = new NavigatorManager( new Singleton() );

			return __instance;
		}

		/**
		 *
		 */
		private var _pathsHistory : Vector.<Object> = new Vector.<Object>();
		/**
		 *
		 */
		private var _activeTransition : Boolean;
		/**
		 *
		 */
		private var _nextArguments : Array;
		/**
		 *
		 */
		private var _nextFunction : Function;

		/**
		 *
		 */
		private var _rootNavigatorScreen : INavigatorScreen = STARLING_MAIN;

		/**
		 *
		 */
		public function get rootNavigatorScreen() : INavigatorScreen
		{
			return _rootNavigatorScreen;
		}

		public function set rootNavigatorScreen( navigatorScreen : INavigatorScreen ) : void
		{
			_rootNavigatorScreen = navigatorScreen;
		}

		/**
		 * @private
		 */
		protected var _historyMaxLength : uint = 50;

		/**
		 *
		 */
		public function get historyMaxLength() : uint
		{
			return _historyMaxLength;
		}

		public function set historyMaxLength( length : uint ) : void
		{
			_historyMaxLength = length;

			if( _pathsHistory.length > _historyMaxLength )
			{
				_pathsHistory.splice( historyMaxLength , _historyMaxLength - _pathsHistory.length + 1 );
			}
		}

		/**
		 *
		 */
		private var _currentPath : String;

		/**
		 *
		 */
		public function get currentPath() : String
		{
			return _currentPath;
		}

		/**
		 * @private
		 */
		protected var _changed : Signal = new Signal( String );

		/**
		 *
		 */
		public function get changed() : Signal
		{
			return _changed;
		}

		/**
		 * @private
		 */
		public function NavigatorManager( singleton : Singleton )
		{
			if( singleton == null ) throw new Error( this + " Singleton instance can only be accessed through getInstance method" );
		}

		/**
		 *
		 */
		public function showPath( path : String , params : * = null , pushToHistory : Boolean = true ) : void
		{
			path = _cleanPath( path );

			if( path == "" || path == null || path == _currentPath ) return;
			if( path.indexOf( "http" ) == 0 )
			{
				navigateToURL( new URLRequest( path ) );
				return;
			}

			if( _activeTransition )
			{
				_nextArguments = arguments;
				_nextFunction = showPath;
				return;
			}

			_nextArguments = null;
			_nextFunction = null;
			_activeTransition = true;

			var screenIDs : Array = path.split( "/" );
			var currentIndex : int = -1;

			params = _normalizeParams( params , screenIDs.length );
			path = "";

			// before going any further save all pages params
			if( _pathsHistory.length > 0 )
			{
				var currentPath : String = _pathsHistory[ _pathsHistory.length - 1 ].url;
				var paths : Array = currentPath.split( "/" );
				var navigatorScreen : INavigatorScreen = _rootNavigatorScreen;
				var screen : IndexedScreen = navigatorScreen.activeScreen as IndexedScreen;
				var cpt : uint;

				for each( var p : String in paths )
				{
					if( screen == null || p != screen.screenID ) break;

					merge( screen.params , _pathsHistory[ _pathsHistory.length - 1 ].params[ cpt ] , true , [ "index" ] );

					if( screen is INavigatorScreen ) screen = ( screen as INavigatorScreen ).activeScreen as IndexedScreen;
					else break;
					cpt++;
				}
			}

			function showNextScreenFromScreen( navigatorScreen : INavigatorScreen ) : void
			{
				currentIndex++;
				var screenParams : Object = params.length > currentIndex ? params[ currentIndex ] : {};
				var screenID : String = screenIDs[ currentIndex ];

				function complete() : void
				{
					navigatorScreen.removeEventListener( FeathersEventType.TRANSITION_COMPLETE , onTransitionComplete );
					var nextScreen : IndexedScreen = navigatorScreen.activeScreen as IndexedScreen;

					path += nextScreen.screenID + "/";

					if( _pathsHistory.length > 0 )
					{
						var currentPath : String = _pathsHistory[ _pathsHistory.length - 1 ].url;
						var indexFromCurrentPath : uint = currentPath.split( "/" ).indexOf( nextScreen.screenID );
						merge( nextScreen.params , _pathsHistory[ _pathsHistory.length - 1 ].params[ indexFromCurrentPath ] , true , [ "index" ] );
					}

					if( nextScreen is INavigatorScreen )
					{
						showNextScreenFromScreen( nextScreen as INavigatorScreen );
					}
					else
					{
						params = _normalizeParams( params , currentIndex + 1 );

						_currentPath = path.substr( 0 , path.length - 1 );

						if( pushToHistory )
						{
							_pathsHistory.push( {url : _currentPath , params : params} );
							while( _pathsHistory.length > _historyMaxLength ) _pathsHistory.shift();
						}

						_activeTransition = false;

						_changed.dispatch( _currentPath );

						if( _nextFunction != null )
						{
							if( _nextArguments != null ) _nextFunction.apply( this , _nextArguments );
							else _nextFunction.apply( this );
						}
					}
				}

				function onTransitionComplete() : void
				{
					setTimeout( complete , 1 );
				}

				if( navigatorScreen.showScreen( screenID , screenParams ) != null ) complete();
				else navigatorScreen.addEventListener( FeathersEventType.TRANSITION_COMPLETE , onTransitionComplete );
			}

			showNextScreenFromScreen( _rootNavigatorScreen );
		}

		/**
		 *
		 */
		public function showScreenID( screenID : String , params : Object = null , pushToHistory : Boolean = true ) : void
		{
			if( _activeTransition )
			{
				_nextArguments = arguments;
				_nextFunction = showScreenID;
				return;
			}

			var aPath : Array = _currentPath.split( "/" );
			aPath.pop();

			var path : String = aPath.join( "/" ) + "/" + screenID;

			var aParams : Array = _pathsHistory.length > 0 ? _pathsHistory[ _pathsHistory.length - 1 ].params.concat() : [];
			aParams.pop();
			aParams.push( params );

			showPath( path , aParams , pushToHistory );
		}

		/**
		 *
		 */
		public function showPreviousScreen( offset : int = 1 ) : void
		{
			if( offset == 0 || _pathsHistory.length <= 1 ) return;

			if( _activeTransition )
			{
				_nextArguments = arguments;
				_nextFunction = showPreviousScreen;
				return;
			}

			if( offset > _pathsHistory.length - 1 ) offset = _pathsHistory.length - 1;

			var index : uint = _pathsHistory.length - 1 - offset;

			var v : Vector.<Object> = _pathsHistory.splice( index , _pathsHistory.length - index );
			var o : Object = v.length > 0 ? v[ 0 ] : null;

			if( o != null ) showPath( o.url , o.params );
		}

		/**
		 *
		 */
		private function _cleanPath( path : String ) : String
		{
			path = path == null ? "" : path;
			path = trim( path );

			if( path.charAt( 0 ) == "/" ) path = path.substr( 1 );
			if( path.charAt( path.length - 1 ) == "/" ) path = path.substr( 0 , path.length - 1 );

			return path;
		}

		/**
		 *
		 */
		private function _normalizeParams( params : * , length : uint ) : Array
		{
			var a : Array = [];
			a.length = length;
			var i : uint;

			if( params is Array )
			{
				for( i = 0; i < length; i++ )
				{
					a[ i ] = i <= params.length - 1 && params[ i ] != null ? params[ i ] : {};
				}
			}
			else
			{
				for( i = 0; i < length; i++ )
				{
					if( i == length - 1 ) a[ i ] = params == null ? {} : params;
					else a[ i ] = {};
				}
			}

			return a;
		}
	}
}

internal class Singleton
{
}
