package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function trimLeft( s : String ) : String
	{
		while( s.charAt( 0 ).match( /\s/ ) )
		{
			s = s.substr( 1 );
		}

		return s;
	}
}
