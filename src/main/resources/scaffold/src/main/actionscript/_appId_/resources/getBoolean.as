package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getBoolean( id : String , groupID : String = EnumScreen.MAIN ) : Boolean
	{
		return getAssetManager( groupID ).getXml( "values" ).bool.( @name == id ).toString() == "true";
	}
}
