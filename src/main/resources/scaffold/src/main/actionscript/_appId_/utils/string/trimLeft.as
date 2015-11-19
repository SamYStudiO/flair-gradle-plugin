package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function trimLeft( s : String ) : String
	{
		while( isWhitespace( s.charAt( 0 ) ) )
		{
			s = s.substr( 1 );
		}

		return s;
	}
}
