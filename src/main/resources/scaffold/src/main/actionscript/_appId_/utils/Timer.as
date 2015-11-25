package _appId_.utils
{
	import flash.display.Shape;
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.TimerEvent;
	import flash.utils.getTimer;

	/**
	 * Dispatched at each Timer interval.
	 */
	[Event(name="timer" , type="flash.events.TimerEvent")]
	/**
	 * Dispatched when timer reach repeatCount property. If repeat is 0 this event is never dispatched, Timer need to be stopped manualy.
	 */
	[Event(name="timerComplete" , type="flash.events.TimerEvent")]
	/**
	 * Timer class is equivalent to flash.utils.Timer class but is syncronize with system clock in order to make easier countdown.
	 * Even if processor is slow down, each intervals are always the same.
	 * @author SamYStudiO ( contact@samystudio.net )
	 */ public class Timer extends EventDispatcher
	{
		/**
		 * @private
		 */
		protected var _frameListener : Shape;

		/**
		 * @private
		 */
		protected var _startTimer : uint;

		/**
		 * @private
		 */
		protected var _offset : uint = 0;

		/**
		 * @private
		 */
		protected var _delay : uint;

		/**
		 * Get or set the delay between each intervals in milliseconds.
		 * If delay is 0 interval is based on stage framerate.
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
		 * Get or set the repeat count (intervals) before Timer stop.
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
		 * Get or set the current time in milliseconds. This is useful when Timer is used as a playhead and you want to reach a time position.
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
		 * Get or set the current count (intervals) elapsed.
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
		 * Get a Boolean that specify if Timer is currently running or stopped.
		 */
		public function get running() : Boolean
		{
			return _frameListener != null;
		}

		/**
		 * Build a new Timer instance.
		 * @param delay the delay between each interval in milliseconds.
		 * @param repeatCount The repeat count before Timer stop automatically.
		 * @param startNow A Boolean that indicates if Timer must start immediatly.
		 */
		public function Timer( delay : uint = 0 , repeatCount : uint = 0 , startNow : Boolean = false )
		{
			_delay = delay;
			_repeatCount = repeatCount;

			if( startNow ) start();
		}

		/**
		 * Start the Timer. If Timer was stopped and not reset, the currentTime and currentCount values
		 * are preserved as they were before Timer stopped.
		 */
		public function start() : void
		{
			if( running ) return;

			_startTimer = getTimer() - _offset;
			_offset = 0;

			_frameListener = new Shape();
			_frameListener.addEventListener( Event.ENTER_FRAME , _enterFrame , false , 0 , true );
		}

		/**
		 * Stop the Timer.
		 */
		public function stop() : void
		{
			if( !running ) return;

			_frameListener.removeEventListener( Event.ENTER_FRAME , _enterFrame );
			_frameListener = null;

			_offset = getTimer() - _startTimer;
		}

		/**
		 * Reset and stop the timer, currentCount and currentTime are reset to 0;
		 */
		public function reset() : void
		{
			_currentCount = _offset = _currentTime = 0;

			if( _frameListener == null ) return;

			_frameListener.removeEventListener( Event.ENTER_FRAME , _enterFrame );
			_frameListener = null;
		}

		/**
		 * @private
		 */
		protected function _enterFrame( e : Event ) : void
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
