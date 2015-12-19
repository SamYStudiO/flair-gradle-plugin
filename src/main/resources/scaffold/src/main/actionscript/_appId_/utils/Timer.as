package _appId_.utils
{
	import flash.display.Shape;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.TimerEvent;
	import flash.utils.getTimer;

	/**
	 *
	 */
	[Event(name="timer" , type="flash.events.TimerEvent")]
	/**
	 *
	 */
	[Event(name="timerComplete" , type="flash.events.TimerEvent")]
	/**
	 *
	 */ public class Timer extends EventDispatcher
	{
		/**
		 *
		 */
		protected var _frameListener : Shape;

		/**
		 *
		 */
		protected var _startTimer : uint;

		/**
		 *
		 */
		protected var _offset : uint = 0;

		/**
		 * @private
		 */
		protected var _delay : uint;

		/**
		 *
		 */
		public function get delay() : uint
		{
			return _delay;
		}

		public function set delay( delay : uint ) : void
		{
			_delay = delay;
		}

		/**
		 * @private
		 */
		protected var _repeatCount : uint;

		/**
		 *
		 */
		public function get repeatCount() : uint
		{
			return _repeatCount;
		}

		public function set repeatCount( repeatCount : uint ) : void
		{
			_repeatCount = repeatCount;
		}

		/**
		 * @private
		 */
		protected var _currentTime : uint;

		/**
		 *
		 */
		public function get currentTime() : uint
		{
			return _currentTime;
		}

		public function set currentTime( time : uint ) : void
		{
			if( _delay == 0 ) return;

			_currentTime = time;
			_currentCount = Math.floor( time / _delay );
			_offset = time % _delay;
			_startTimer = _startTimer - _offset;
		}

		/**
		 * @private
		 */
		protected var _currentCount : uint;

		/**
		 *
		 */
		public function get currentCount() : uint
		{
			return _currentCount;
		}

		public function set currentCount( count : uint ) : void
		{
			if( _delay == 0 ) return;

			_currentCount = count;
			_currentTime = count * _delay;
		}

		/**
		 *
		 */
		public function get running() : Boolean
		{
			return _frameListener != null;
		}

		/**
		 *
		 */
		public function Timer( delay : uint = 0 , repeatCount : uint = 0 , startNow : Boolean = false )
		{
			_delay = delay;
			_repeatCount = repeatCount;

			if( startNow ) start();
		}

		/**
		 *
		 */
		public function start() : void
		{
			if( running ) return;

			_startTimer = getTimer() - _offset;
			_offset = 0;

			_frameListener = new Shape();
			_frameListener.addEventListener( Event.ENTER_FRAME , _tick , false , 0 , true );
		}

		/**
		 *
		 */
		public function stop() : void
		{
			if( !running ) return;

			_frameListener.removeEventListener( Event.ENTER_FRAME , _tick );
			_frameListener = null;

			_offset = getTimer() - _startTimer;
		}

		/**
		 *
		 */
		public function reset() : void
		{
			_currentCount = _offset = _currentTime = 0;

			if( _frameListener == null ) return;

			_frameListener.removeEventListener( Event.ENTER_FRAME , _tick );
			_frameListener = null;
		}

		/**
		 *
		 */
		protected function _tick( e : Event ) : void
		{
			var timer : int = getTimer();

			if( timer >= _startTimer + _delay || _delay == 0 )
			{
				_currentCount++;
				_currentTime += timer - _startTimer;
				_startTimer = timer;

				dispatchEvent( new TimerEvent( TimerEvent.TIMER ) );

				if( _currentCount >= _repeatCount && _repeatCount != 0 )
				{
					dispatchEvent( new TimerEvent( TimerEvent.TIMER_COMPLETE ) );

					reset();
				}
			}
		}
	}
}
