package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function trimLeft( s : String ) : String
	{
		return s.replace( /(^\s+)/ , "" );
	}
}
