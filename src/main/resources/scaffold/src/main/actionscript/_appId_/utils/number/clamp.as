package _appId_.utils.number
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function clamp( n : Number , min : Number , max : Number ) : Number
	{
		return Math.max( min , Math.min( n , max ) );
	}
}
