package _appId_.utils.string
{
	/**
	 *
	 */
	public function trimRight( s : String ) : String
	{
		return s.replace( /(\s+$)/ , "" );
	}
}
