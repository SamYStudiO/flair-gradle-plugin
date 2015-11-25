package _appId_.utils.number
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function clamp( n : Number , min : Number = Number.NEGATIVE_INFINITY , max : Number = Number.POSITIVE_INFINITY , throwError : Boolean = false ) : Number
	{
		if( throwError && ( n < min || n > max || isNaN( n ) ) ) throw new Error( "Number " + n + " is out of range; minimum > " + min + "; maximum > " + max );

		if( n < min || isNaN( n ) ) n = min;
		else if( n > max ) n = max;

		return n;
	}
}
