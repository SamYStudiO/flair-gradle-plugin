package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function trimRight( s : String ) : String
	{
		while( s.charAt( s.length - 1 ).match( /\s/ ) )
		{
			s = s.substr( 0 , s.length - 1 );
		}

		return s;
	}
}
