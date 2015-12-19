package _appId_.utils.string
{
	/**
	 *
	 */
	public function isUrl( s : String ) : Boolean
	{
		return /^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/i.test( s );
	}
}
