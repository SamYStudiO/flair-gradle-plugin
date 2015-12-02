package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function trimRight( s : String ) : String
	{
		return s.replace( /(\s+$)/ , "" );
	}
}
