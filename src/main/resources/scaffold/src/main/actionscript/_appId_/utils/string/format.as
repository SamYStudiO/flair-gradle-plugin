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
			var s : String = substitutes[ i ];

			s.replace( "/\\{" + i + "\\}/g" , s )
		}

		return s;
	}
}
