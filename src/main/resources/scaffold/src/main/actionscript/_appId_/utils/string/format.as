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
			var substitute : String = substitutes[ i ];

			while( s.indexOf( "{" + i + "}" ) >= 0 )
				s = s.replace( "{" + i + "}" , substitute )
		}

		return s;
	}
}
