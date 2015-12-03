package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function format( s : String , ...substitutes ) : String
	{
		var l : uint = substitutes.length;

		for( var i : int = 0; i < l; i++ )
		{
			s = s.replace( new RegExp( "\\{" + i + "\\}" , "g" ) , substitutes[ i ] )
		}

		return s;
	}
}
