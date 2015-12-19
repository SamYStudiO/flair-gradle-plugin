package _appId_.utils.string
{
	/**
	 *
	 */
	public function trim( s : String ) : String
	{
		return trimRight( trimLeft( s ) );
	}
}
