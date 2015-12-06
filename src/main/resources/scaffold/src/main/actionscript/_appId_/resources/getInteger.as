package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getInteger( id : String , groupID : String = EnumScreen.MAIN ) : int
	{
		return int( getAssetManager( groupID ).getXml( "values" )..integer.( @name == id ) );
	}
}
