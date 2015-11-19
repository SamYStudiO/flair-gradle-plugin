package _appId_.utils.string
{
	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function isWhitespace( s : String ) : Boolean
	{
		switch( s )
		{
			case " "  :
			case "\t" :
			case "\r" :
			case "\n" :
			case "\f" :
				return true;
			default :
				return false;
		}
	}
}
