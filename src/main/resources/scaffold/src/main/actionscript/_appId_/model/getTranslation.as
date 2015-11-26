package _appId_.model
{
	import _appId_.view.EnumScreen;

	/**
	 * @author SamYStudiO ( contact@samystudio.net )
	 */
	public function getTranslation( id : String , groupID : String = EnumScreen.MAIN ) : String
	{
		return getAssetManager( groupID ).getXml( "strings" ).child( id ).toString();
	}
}
