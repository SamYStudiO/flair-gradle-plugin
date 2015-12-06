package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getString( id : String , groupID : String = EnumScreen.MAIN ) : String
	{
		return getAssetManager( groupID ).getXml( "values" )..strings.( @name == id ).toString();
	}
}
