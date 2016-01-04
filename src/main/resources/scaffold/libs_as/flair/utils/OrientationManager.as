package flair.utils
{
	import flash.display.Stage;
	import flash.display.StageOrientation;
	import flash.events.Event;
	import flash.geom.Matrix;
	import flash.utils.Dictionary;

	import org.osflash.signals.Signal;

	/**
	 *
	 */
	public final class OrientationManager
	{
		/**
		 *
		 */
		private static var __instances : Dictionary = new Dictionary( true );

		/**
		 *
		 */
		public static function getInstance( stage : Stage ) : OrientationManager
		{
			if( !__instances[ stage ] ) __instances[ stage ] = new OrientationManager( new Singleton() , stage );

			return __instances[ stage ];
		}

		/**
		 *
		 */
		private const _orientations : Array = [ StageOrientation.DEFAULT , StageOrientation.ROTATED_LEFT , StageOrientation.UPSIDE_DOWN , StageOrientation.ROTATED_RIGHT ];

		/**
		 *
		 */
		private var _stage : Stage;

		/**
		 *
		 */
		private var _stageOrientation : String;

		/**
		 *
		 */
		public function get stageOrientation() : String
		{
			return _stageOrientation;
		}

		public function set stageOrientation( s : String ) : void
		{
			_stage.setOrientation( s );
		}

		/**
		 *
		 */
		private var _deviceOrientation : String;

		/**
		 *
		 */
		public function get deviceOrientation() : String
		{
			return _deviceOrientation;
		}

		/**
		 *
		 */
		public function get isStagePortrait() : Boolean
		{
			return _stage.stageWidth < _stage.stageHeight;
		}

		/**
		 *
		 */
		public function get isStageLandscape() : Boolean
		{
			return _stage.stageWidth > _stage.stageHeight;
		}

		/**
		 *
		 */
		public function get isDevicePortrait() : Boolean
		{
			return ( _defaultOrientationIsPortrait && ( _deviceOrientation == StageOrientation.DEFAULT || _deviceOrientation == StageOrientation.UPSIDE_DOWN ) ) || ( !_defaultOrientationIsPortrait && ( _deviceOrientation == StageOrientation.ROTATED_LEFT || _deviceOrientation == StageOrientation.ROTATED_RIGHT ) );
		}

		/**
		 *
		 */
		public function get isDeviceLandscape() : Boolean
		{
			return !isDevicePortrait;
		}

		/**
		 *
		 */
		private var _defaultOrientationIsPortrait : Boolean;

		/**
		 *
		 */
		public function get defaultOrientationIsPortrait() : Boolean
		{
			return _defaultOrientationIsPortrait;
		}

		/**
		 *
		 */
		public function get defaultOrientationIsLandscape() : Boolean
		{
			return !_defaultOrientationIsPortrait;
		}

		/**
		 *
		 */
		private var _deviceVSStageMatrix : Matrix;

		/**
		 *
		 */
		public function get deviceVSStageMatrix() : Matrix
		{
			return _deviceVSStageMatrix.clone();
		}

		/**
		 *
		 */
		private var _stageOrientationChanged : Signal = new Signal();

		/**
		 *
		 */
		public function get stageOrientationChanged() : Signal
		{
			return _stageOrientationChanged;
		}

		/**
		 *
		 */
		private var _deviceOrientationChanged : Signal = new Signal();

		/**
		 *
		 */
		public function get deviceOrientationChanged() : Signal
		{
			return _deviceOrientationChanged;
		}

		/**
		 * @private
		 */
		public function OrientationManager( singleton : Singleton , stage : Stage )
		{
			if( !singleton ) throw new Error( this + " Singleton instance can only be accessed through getInstance method" );

			_stage = stage;

			_stageOrientation = _stage.orientation == StageOrientation.UNKNOWN ? StageOrientation.DEFAULT : _stage.orientation;

			var orientationIsDefaultUpDown : Boolean = _stageOrientation == StageOrientation.DEFAULT || _stageOrientation == StageOrientation.UPSIDE_DOWN;
			var orientationIsRightLeft : Boolean = _stageOrientation == StageOrientation.ROTATED_LEFT || _stageOrientation == StageOrientation.ROTATED_RIGHT;

			_defaultOrientationIsPortrait = orientationIsDefaultUpDown && isStagePortrait || orientationIsRightLeft && isStageLandscape;
			_deviceOrientation = _stage.deviceOrientation == StageOrientation.UNKNOWN ? ( _stageOrientation == StageOrientation.ROTATED_RIGHT ? StageOrientation.ROTATED_LEFT : _stageOrientation == StageOrientation.ROTATED_LEFT ? StageOrientation.ROTATED_RIGHT : _stageOrientation ) : _stage.deviceOrientation;
			_deviceVSStageMatrix = new Matrix();
		}

		/**
		 *
		 */
		public function startListeningForChange() : void
		{
			_stage.addEventListener( Event.ENTER_FRAME , _monitorOrientation );
		}

		/**
		 *
		 */
		public function stopListeningForChange() : void
		{
			_stage.removeEventListener( Event.ENTER_FRAME , _monitorOrientation );
		}

		/**
		 *
		 */
		public function updateStageOrientationFromDeviceOrientation() : void
		{
			stageOrientation = _deviceOrientation == StageOrientation.ROTATED_LEFT ? StageOrientation.ROTATED_RIGHT : _deviceOrientation == StageOrientation.ROTATED_RIGHT ? StageOrientation.ROTATED_LEFT : _deviceOrientation;
		}

		/**
		 *
		 */
		private function _monitorOrientation( e : Event = null ) : void
		{
			var newStageOrientation : String = _stage.orientation == StageOrientation.UNKNOWN ? _stageOrientation : _stage.orientation;
			var stageWidth : Number = _stage.stageWidth;
			var stageHeight : Number = _stage.stageHeight;
			var stageIsPortrait : Boolean = ( _defaultOrientationIsPortrait && ( newStageOrientation == StageOrientation.DEFAULT || newStageOrientation == StageOrientation.UPSIDE_DOWN ) ) || ( !_defaultOrientationIsPortrait && ( newStageOrientation == StageOrientation.ROTATED_LEFT || newStageOrientation == StageOrientation.ROTATED_RIGHT ) );
			var stageOrientationChanged : Boolean = _stageOrientation != newStageOrientation && ( ( stageIsPortrait && stageWidth < stageHeight ) || ( !stageIsPortrait && stageWidth >= stageHeight ) );

			var newDeviceOrientation : String = _stage.deviceOrientation == StageOrientation.UNKNOWN ? _deviceOrientation : _stage.deviceOrientation;
			var deviceOrientationChanged : Boolean = _deviceOrientation != newDeviceOrientation;

			if( stageOrientationChanged || deviceOrientationChanged )
			{
				_stageOrientation = newStageOrientation;
				_deviceOrientation = newDeviceOrientation;

				var deviceOrientationFromStage : String = _stageOrientation == StageOrientation.ROTATED_LEFT ? StageOrientation.ROTATED_RIGHT : _stageOrientation == StageOrientation.ROTATED_RIGHT ? StageOrientation.ROTATED_LEFT : _stageOrientation;
				var offset : uint = _orientations.indexOf( deviceOrientationFromStage );
				var currentIndex : int = _orientations.indexOf( _deviceOrientation );

				currentIndex -= offset;

				if( currentIndex > _orientations.length - 1 ) currentIndex -= _orientations.length;
				if( currentIndex < 0 ) currentIndex += _orientations.length;

				var orientation : String = _orientations[ currentIndex ];

				_deviceVSStageMatrix.identity();

				if( orientation == StageOrientation.ROTATED_LEFT )
				{
					_deviceVSStageMatrix.rotate( Math.PI / 2 );
					_deviceVSStageMatrix.translate( 1 , 0 );
				}
				else if( orientation == StageOrientation.ROTATED_RIGHT )
				{
					_deviceVSStageMatrix.rotate( -Math.PI / 2 );
					_deviceVSStageMatrix.translate( 0 , 1 );
				}
				else if( orientation == StageOrientation.UPSIDE_DOWN )
				{
					_deviceVSStageMatrix.rotate( Math.PI );
					_deviceVSStageMatrix.translate( 1 , 1 );
				}

				if( stageOrientationChanged ) this.stageOrientationChanged.dispatch();

				if( deviceOrientationChanged ) this.deviceOrientationChanged.dispatch();
			}
		}
	}
}

class Singleton
{
}
