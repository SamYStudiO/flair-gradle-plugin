package _appId_.utils.string
{
	/**
	 *
	 */
	public function trimLeft( s : String ) : String
	{
		return s.replace( /(^\s+)/ , "" );
	}
}
