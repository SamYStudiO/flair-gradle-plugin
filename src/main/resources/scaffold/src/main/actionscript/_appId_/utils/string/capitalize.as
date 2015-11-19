package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function capitalize( s : String ) : String
	{
		return s.charAt( 0 ).toUpperCase() + s.substr( 1 );
	}
}
