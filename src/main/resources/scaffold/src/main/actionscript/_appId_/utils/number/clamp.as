package _appId_.utils.number
{
	/**
	 *
	 */
	public function clamp( n : Number , min : Number , max : Number ) : Number
	{
		return Math.max( min , Math.min( n , max ) );
	}
}
