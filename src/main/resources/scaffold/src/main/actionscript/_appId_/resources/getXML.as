package _appId_.resources
{
	import _appId_.view.EnumScreen;

	/**
	 *
	 */
	public function getXml( id : String , groupID : String = EnumScreen.MAIN ) : String
	{
		return getAssetManager( groupID ).getXml( id );
	}
}
