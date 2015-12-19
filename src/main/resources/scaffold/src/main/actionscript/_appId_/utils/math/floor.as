package _appId_.utils.math
{
	/**
	 *
	 */
	public function floor( n : Number ) : int
	{
		var ni : int = n;
		return ( n < 0 && n != ni ) ? ni - 1 : ni;
	}
}
