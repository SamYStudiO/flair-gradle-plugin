package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function digitFormat( n : Number , length : uint = 2 ) : String
	{
		var a : Array = n.toString().split( "." );
		var s : String = isNaN( n ) ? "" : a[ 0 ];

		while( s.length < length ) s = "0" + s;

		return s + ( a.length > 1 && !isNaN( n ) ? "." + a[ 1 ] : "" );
	}
}
